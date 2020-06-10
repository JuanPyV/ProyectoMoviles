package app.project.traverio.Dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
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
import com.xw.repo.BubbleSeekBar;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class InfoFriendDialog extends AppCompatDialogFragment {

    public static final String ARGUMENTO_USER = "User";
    public static final String ARGUMENTO_NOMBRE = "Nombre";
    public static final String ARGUMENTO_LNAME = "Apellido";
    public static final String ARGUMENTO_LOCATION = "Ubicacion";
    public static final String ARGUMENTO_AGE = "Edad";
    public static final String ARGUMENTO_URL = "URL";
    public static final String ARGUMENTO_UID = "UID";
    public static final String ARGUMENTO_RATING = "RATING";

    private String usuario, nombre, apellido, ubicacion, edad, url, uid;
    private double rating;
    private TextView username, name, lname, age, ubic, ratingNum;
    private ImageView placeImage;
    private Button btnRate;
    private double rate = 1;
    private BubbleSeekBar scoreSeek;
    private RatingBar rateBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public static InfoFriendDialog newInstance(String user, String nombre, String apellido, String ubicacion, String edad, String url, String UID, double rating2) {
        InfoFriendDialog fragment = new InfoFriendDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENTO_USER, user);
        bundle.putString(ARGUMENTO_NOMBRE, nombre);
        bundle.putString(ARGUMENTO_LNAME, apellido);
        bundle.putString(ARGUMENTO_LOCATION, ubicacion);
        bundle.putString(ARGUMENTO_AGE, edad);
        bundle.putString(ARGUMENTO_URL, url);
        bundle.putString(ARGUMENTO_UID, UID);
        bundle.putDouble(ARGUMENTO_RATING, rating2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        usuario = args.getString(ARGUMENTO_USER);
        nombre = args.getString(ARGUMENTO_NOMBRE);
        apellido = args.getString(ARGUMENTO_LNAME);
        ubicacion = args.getString(ARGUMENTO_LOCATION);
        edad = args.getString(ARGUMENTO_AGE);
        url = args.getString(ARGUMENTO_URL);
        uid = args.getString(ARGUMENTO_UID);
        rating = args.getDouble(ARGUMENTO_RATING);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialoginfofriend, null);

        username = v.findViewById(R.id.usuario);
        name = v.findViewById(R.id.name);
        lname = v.findViewById(R.id.lname);
        ubic = v.findViewById(R.id.ubic);
        age = v.findViewById(R.id.age);
        placeImage = v.findViewById(R.id.placeImage);
        ratingNum = v.findViewById(R.id.ratingNum);
        btnRate = v.findViewById(R.id.btnRate);
        scoreSeek = v.findViewById(R.id.bubbleSeekBar);
        rateBar = v.findViewById(R.id.ratingBar);

        try{
            Picasso.get().load(url).error(R.drawable.charlie_chaplin).into(placeImage);
        } catch(IllegalArgumentException e){
            Picasso.get().load(R.drawable.charlie_chaplin).into(placeImage);
        }
        username.setText(usuario);
        name.setText(nombre);
        lname.setText(apellido);
        age.setText(edad);
        ubic.setText(ubicacion);
        DecimalFormat df = new DecimalFormat("#.#");
        ratingNum.setText(df.format(rating));
        rateBar.setRating((float) rating);
        scoreSeek.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                rate = progress;
            }
            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }
            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratePerson();
            }
        });

        builder.setView(v);

        return builder.create();
    }

    public void setRating(List<PersonRating> list){
        double average = 0;
        for (int i = 0; i < list.size(); i++){
            average += list.get(i).getRating();
        }
        average /= list.size();
        ratingNum.setText(average + "");
        rateBar.setRating((float) average);
    }

    public void ratePerson(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users").child(uid).child("UserInformation");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                List<PersonRating> personRatingList = userInformation.getPersonRatings();
                if (personRatingList != null){
                    boolean canRate = true;
                    for (int i = 0; i < personRatingList.size(); i++){
                        if (personRatingList.get(i).getUID().equals(user.getUid())){
                            canRate = false;
                        }
                    }
                    if (canRate){
                        personRatingList.add(new PersonRating(rate, user.getUid()));
                        userInformation.setPersonRatings(personRatingList);
                        setRating(personRatingList);
                        Toast.makeText(getActivity(), "You just rated your friend!", Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(getActivity(), "You can only vote your friend once.", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    personRatingList = new LinkedList<>();
                    personRatingList.add(new PersonRating(rate, user.getUid()));
                    userInformation.setPersonRatings(personRatingList);
                    setRating(personRatingList);
                    Toast.makeText(getActivity(), "You just rated your friend!", Toast.LENGTH_SHORT).show();
                }
                dataSnapshot.getRef().setValue(userInformation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}