package com.example.aplicacioncitaconmemin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.database.ValueEventListener;

public class FragmentPerInfo extends Fragment {
    @Nullable


    private TextView usernameP;
    private TextView firstNameP;
    private TextView lastNameP;
    private TextView locationP;
    private TextView ageP;
    private TextView bioP;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perinfo, container, false);
        usernameP = v.findViewById(R.id.usernameP);
        firstNameP = v.findViewById(R.id.firstNameP);
        lastNameP = v.findViewById(R.id.lastNameP);
        locationP = v.findViewById(R.id.locationP);
        ageP = v.findViewById(R.id.ageP);
        bioP = v.findViewById(R.id.bioP);
        System.out.println("estoy siendo creado");



        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation information = dataSnapshot.child(user.getUid()).child("UserInformation").getValue(UserInformation.class);
                System.out.println("intentando cargar datos");
                if (information != null) {
                    System.out.println("el objeto no es nulo, cargando datos, tal vez sean vacios");
                    usernameP.setText(information.getUsername());
                    firstNameP.setText(information.getFirstName());
                    lastNameP.setText(information.getLastName());
                    locationP.setText(information.getLocation());
                    ageP.setText(information.getAge());
                    bioP.setText(information.getPersonalInformation());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }





}
