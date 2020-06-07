package com.example.aplicacioncitaconmemin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aplicacioncitaconmemin.Adapters.FriendAdapter;
import com.example.aplicacioncitaconmemin.Dialogs.InfoFriendDialog;
import com.example.aplicacioncitaconmemin.AuxiliaryClasses.PersonRating;
import com.example.aplicacioncitaconmemin.R;
import com.example.aplicacioncitaconmemin.AuxiliaryClasses.UserInformation;
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


public class FragmentFriendList extends Fragment {

    private ListView friends;
    //private ArrayAdapter<UserInformation> usernames;
    private FriendAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        friends = v.findViewById(R.id.friendList);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        fillListView();
        friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInformation item = adapter.getItem(position);
                String username = item.getUsername();
                String firstName = item.getFirstName();
                String lastName = item.getLastName();
                String location = item.getLocation();
                String age = item.getAge();
                String url = item.getProfilePicURL() + "";
                String uid = item.getUID();
                double average = 0;
                List<PersonRating> list = item.getPersonRatings();
                if (list != null){
                    for (int i = 0; i < list.size(); i++){
                        average += list.get(i).getRating();
                    }
                    average /= list.size();
                }
                InfoFriendDialog infoFriendDialog = new InfoFriendDialog();
                infoFriendDialog.newInstance(username, firstName,lastName,location,age, url, uid, average).show(getFragmentManager(), "infoFriend dialog");
                //Log.wtf("tag", "Nombre: " + firstName + " Apellido: " + lastName + " Location: " + location + " Edad: " + age + " URL: " + url);
                //Toast.makeText(getActivity(), "Nombre: " + firstName + "  / Apellido: " + lastName + "  / Location: " + location + "  / Edad: " + age, Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    private void fillListView(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<UserInformation>> t = new GenericTypeIndicator<List<UserInformation>>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                };
                List<UserInformation> lista = dataSnapshot.child(user.getUid()).child("Friends").getValue(t);
                /*
                como anotacion:
                guardabamos en la base de datos una instancia del objeto UserInformation en una lista que se almacenaba dentro del UID del usuario
                que en cuestion estaba agregando a ese amigo, pero debido a que esa instancia de guardaba unicamente al añadir al amigo, lo que pasaba
                es que los datos de esa persona, se quedaban fijos al momento de agregarla, de manera que si editaban cualquier parte de su informacion
                esto no se mostraria en la lista de amigos. Idealmente, la solución sería únicamente almacenar el UID del amigo como referencia, pero
                debido al tiempo restante dejaré que se guarden todos los datos.
                 */
                List<UserInformation> lista2 = new ArrayList<>();
                if (lista != null){
                    for (int i = 0; i < lista.size(); i++){
                        String UID = lista.get(i).getUID();
                        UserInformation friend = dataSnapshot.child(UID).child("UserInformation").getValue(UserInformation.class);
                        lista2.add(friend);
                    }
                    //adapter = new FriendAdapter(getActivity(), lista); anterior, para corregir bug de url de imagen de amigo estatica al añadirlo
                    adapter = new FriendAdapter(getActivity(), lista2);
                    //usernames = new ArrayAdapter<UserInformation>(getActivity(), android.R.layout.simple_expandable_list_item_1, lista);
                    friends.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
