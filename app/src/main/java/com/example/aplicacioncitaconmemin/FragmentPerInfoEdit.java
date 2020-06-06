package com.example.aplicacioncitaconmemin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentPerInfoEdit extends Fragment {
    @Nullable


    private EditText username;
    private EditText firstName;
    private EditText lastName;
    private EditText location;
    private EditText age;
    private EditText bio;
    private EditText photoEditURL;
    private Button submit;
    private CircleImageView photo;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perinfoedit, container, false);
        username = v.findViewById(R.id.usernameInicio);
        firstName = v.findViewById(R.id.firstname);
        lastName = v.findViewById(R.id.lastname);
        location = v.findViewById(R.id.location);
        age = v.findViewById(R.id.age);
        bio = v.findViewById(R.id.bio);
        submit = v.findViewById(R.id.buttonSubmit);
        photo = v.findViewById(R.id.photoEdit);
        photoEditURL = v.findViewById(R.id.photoEditURL);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInformation();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation information = dataSnapshot.child(user.getUid()).child("UserInformation").getValue(UserInformation.class);
                if (information != null) {
                    username.setText(information.getUsername());
                    firstName.setText(information.getFirstName());
                    lastName.setText(information.getLastName());
                    location.setText(information.getLocation());
                    age.setText(information.getAge());
                    bio.setText(information.getPersonalInformation());
                    try{
                        photoEditURL.setText(information.getProfilePicURL());
                        Picasso.get().load(information.getProfilePicURL() + "").resize(94, 94).centerCrop().error(R.drawable.charlie_chaplin).into(photo);
                    } catch (IllegalArgumentException e){
                        Picasso.get().load(R.drawable.charlie_chaplin).resize(94, 94).centerCrop().into(photo);
                    } catch (NullPointerException e){
                        Picasso.get().load(R.drawable.charlie_chaplin).resize(94, 94).centerCrop().into(photo);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }

    private void createInformation(){
        String username2 = username.getText().toString();
        String firstName2 = firstName.getText().toString();
        String lastName2 = lastName.getText().toString();
        String location2 = location.getText().toString();
        String age2 = age.getText().toString();
        String bio2 = bio.getText().toString();
        String url2 = photoEditURL.getText().toString();
        UserInformation info = new UserInformation(username2, location2, firstName2, lastName2, age2, user.getUid(), bio2);
        info.setProfilePicURL(url2);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).child("UserInformation").setValue(info);
        Toast.makeText(getActivity(), "Information updated!", Toast.LENGTH_SHORT).show();
    }



}
