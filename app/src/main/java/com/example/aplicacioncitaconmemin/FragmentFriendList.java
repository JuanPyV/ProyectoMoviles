package com.example.aplicacioncitaconmemin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

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
                String firstName = item.getFirstName();
                String lastName = item.getLastName();
                String location = item.getLocation();
                String age = item.getAge();
                InfoFriendDialog infoFriendDialog = new InfoFriendDialog();
                infoFriendDialog.newInstance(firstName,lastName,location,age, "https://picsum.photos/65/65?random=1").show(getFragmentManager(), "infoFriend dialog");
                Log.wtf("tag", "Nombre: " + firstName + " Apellido: " + lastName + " Location: " + location + " Edad: " + age);
                Toast.makeText(getActivity(), "Nombre: " + firstName + "  / Apellido: " + lastName + "  / Location: " + location + "  / Edad: " + age, Toast.LENGTH_LONG).show();
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
                if (lista != null){
                    adapter = new FriendAdapter(getActivity(), lista);
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
