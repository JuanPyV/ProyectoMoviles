package app.project.traverio.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import app.project.traverio.Activities.MainActivity;
import app.project.traverio.AuxiliaryClasses.PersonRating;
import app.project.traverio.R;
import app.project.traverio.AuxiliaryClasses.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentPerInfo extends Fragment {
    @Nullable


    private TextView usernameP;
    private TextView firstNameP;
    private TextView lastNameP;
    private TextView locationP;
    private TextView ageP;
    private TextView bioP;
    private CircleImageView imageP;
    private ImageButton exitButton;
    private RatingBar rateBar;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perinfo, container, false);
        usernameP = v.findViewById(R.id.usernameP);
        firstNameP = v.findViewById(R.id.firstNameP);
        lastNameP = v.findViewById(R.id.lastNameP);
        locationP = v.findViewById(R.id.locationP);
        ageP = v.findViewById(R.id.ageP);
        bioP = v.findViewById(R.id.bioP);
        imageP = v.findViewById(R.id.imageP);
        exitButton = v.findViewById(R.id.exitButton);
        rateBar = v.findViewById(R.id.ratingBarP);

        mAuth = FirebaseAuth.getInstance();


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                Intent intentito = new Intent(getActivity(), MainActivity.class);
                startActivity(intentito);
                Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();


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
                    usernameP.setText(information.getUsername());
                    firstNameP.setText(information.getFirstName());
                    lastNameP.setText(information.getLastName());
                    locationP.setText(information.getLocation());
                    ageP.setText(information.getAge());
                    bioP.setText(information.getPersonalInformation());

                    try{
                        Picasso.get().load(information.getProfilePicURL() + "").resize(94, 94).centerCrop().error(R.drawable.charlie_chaplin).into(imageP);
                    } catch (IllegalArgumentException e){
                        Picasso.get().load(R.drawable.charlie_chaplin).resize(94, 94).centerCrop().into(imageP);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loadStar();

        return v;
    }

    public void loadStar(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users").child(user.getUid()).child("UserInformation");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                if (userInformation != null){
                    List<PersonRating> personRatingList = userInformation.getPersonRatings();
                    double average = 0;
                    if (personRatingList != null){
                        for(int i = 0; i < personRatingList.size(); i++){
                            average += personRatingList.get(i).getRating();
                        }
                        average /= personRatingList.size();
                        rateBar.setRating((float) average);
                    }else{
                        rateBar.setRating((float) average);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
