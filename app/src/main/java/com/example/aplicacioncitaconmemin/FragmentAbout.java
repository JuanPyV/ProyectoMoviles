package com.example.aplicacioncitaconmemin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentAbout extends Fragment {

    private EditText location;
    private ListView information;
    private Button search;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about,container,false);
       location = v.findViewById(R.id.location);
       information = v.findViewById(R.id.peopleData);
       search = v.findViewById((R.id.search));

       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               searchByLocation();
           }
       });



        return v;
    }

    private void searchByLocation(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");
        final String searchLocation = location.getText().toString().toLowerCase();
        //System.out.println(searchLocation);
        final List<String> users = new ArrayList<String>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objDataSnapshot : dataSnapshot.getChildren()){
                    UserInformation userInformation = objDataSnapshot.getValue(UserInformation.class);
                    if (userInformation.getLocation().equals(searchLocation)){
                        users.add(userInformation.getUsername());
                    }
                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, users);
                    information.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //System.out.println(users.size() + " es el tamaño de coincidencias");

    }


}
