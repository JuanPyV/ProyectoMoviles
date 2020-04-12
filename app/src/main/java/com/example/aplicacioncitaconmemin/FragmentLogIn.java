package com.example.aplicacioncitaconmemin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class FragmentLogIn extends Fragment {
    @Nullable


    private EditText username;
    private EditText firstName;
    private EditText lastName;
    private EditText location;
    private EditText age;
    private Button submit;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        username = v.findViewById(R.id.usernameInicio);
        firstName = v.findViewById(R.id.firstname);
        lastName = v.findViewById(R.id.lastname);
        location = v.findViewById(R.id.location);
        age = v.findViewById(R.id.age);
        submit = v.findViewById(R.id.buttonSubmit);
        System.out.println("estoy siendo creado");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInformation();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation information = dataSnapshot.child(user.getUid()).getValue(UserInformation.class);
                System.out.println("intentando cargar datos");
                if (information != null) {
                    System.out.println("el objeto no es nulo, cargando datos, tal vez sean vacios");
                    username.setText(information.getUsername());
                    firstName.setText(information.getFirstName());
                    lastName.setText(information.getLastName());
                    location.setText(information.getLocation());
                    age.setText(information.getAge());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }

    private void createInformation(){
        String username2 = username.getText().toString().toLowerCase();
        String firstName2 = firstName.getText().toString().toLowerCase();
        String lastName2 = lastName.getText().toString().toLowerCase();
        String location2 = location.getText().toString().toLowerCase();
        String age2 = age.getText().toString().toLowerCase();
        UserInformation info = new UserInformation(username2, location2, firstName2, lastName2, age2);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(info);
        Toast.makeText(getActivity(), "Informacion actualizada!", Toast.LENGTH_SHORT).show();
    }


}
