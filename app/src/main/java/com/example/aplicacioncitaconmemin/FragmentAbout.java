package com.example.aplicacioncitaconmemin;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentAbout extends Fragment {

    private EditText location;
    private ListView information;
    private Button search;
    private ArrayAdapter<FriendInformation> adapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about,container,false);
       location = v.findViewById(R.id.location);
       information = v.findViewById(R.id.peopleData);
       search = v.findViewById((R.id.search));
       firebaseAuth = FirebaseAuth.getInstance();
       user = firebaseAuth.getCurrentUser();
       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               searchByLocation();
           }
       });
       information.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               FriendInformation item = adapter.getItem(position);
               if (!item.getUID().equals(user.getUid())){
                   //añadir amigo
                   updateFriendList(item.getUID());
               } else{
                   Toast.makeText(getActivity(), "No puedes añadirte a ti mismo como amigo", Toast.LENGTH_SHORT).show();
               }

           }
       });


        return v;
    }

    private void updateFriendList(final String friendUID){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference().child("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //obteniendo informacion de usuario activo para modificarla
                GenericTypeIndicator<List<UserInformation>> t = new GenericTypeIndicator<List<UserInformation>>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                };
                List<UserInformation> lista = dataSnapshot.child(user.getUid()).child("Friends").getValue(t);
                if (lista == null){
                    lista = new ArrayList<>();
                }
                for (DataSnapshot objDataSnapshot : dataSnapshot.getChildren()){
                    Log.wtf("compare", "COMPARANDO UID DE AMIGO: " + friendUID + " con UID: " + objDataSnapshot.getKey());
                    if (friendUID.equals(objDataSnapshot.getKey())){
                        UserInformation friendUserInformation = objDataSnapshot.child("UserInformation").getValue(UserInformation.class);
                        if (lista.size() == 0){ //en el caso de que sea la primera
                            lista.add(friendUserInformation);
                            Toast.makeText(getActivity(), "Amigo añadido exitosamente", Toast.LENGTH_SHORT).show();
                        } else{
                            int flag = 0;
                            for (int i = 0; i < lista.size(); i++){
                                if(lista.get(i).getUID().equals(friendUserInformation.getUID())){
                                    flag++;
                                }
                            }
                            if (flag == 0){
                                lista.add(friendUserInformation);
                                Toast.makeText(getActivity(), "Amigo añadido exitosamente", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(getActivity(), "Esta persona ya es tu amiga", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Log.wtf("a", lista.size() + " tamaño de lista " + lista.get(0).getUsername());
                        databaseReference.child(user.getUid()).child("Friends").setValue(lista);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchByLocation(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");
        final String searchLocation = location.getText().toString().toLowerCase();
        //final List<String> users = new ArrayList<>(); viejo arraylist con string solamente
        final List<FriendInformation> users2 = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objDataSnapshot : dataSnapshot.getChildren()){
                    UserInformation userInformation = objDataSnapshot.child("UserInformation").getValue(UserInformation.class);
                    if (userInformation.getLocation().equals(searchLocation)){
                        //users.add(userInformation.getUsername()); viejo user
                        users2.add(new FriendInformation(objDataSnapshot.getKey(), userInformation.getUsername()));
                    }
                    //adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, users);  viejo adaptaer
                    adapter = new ArrayAdapter<FriendInformation>(getActivity(), android.R.layout.simple_list_item_1, users2);
                    information.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
