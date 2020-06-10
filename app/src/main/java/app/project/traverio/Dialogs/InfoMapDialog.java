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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import app.project.traverio.Fragments.FragmentMap;
import app.project.traverio.AuxiliaryClasses.PersonRating;
import app.project.traverio.AuxiliaryClasses.PlaceInformation;
import app.project.traverio.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.xw.repo.BubbleSeekBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class InfoMapDialog extends AppCompatDialogFragment {

    public static final String ARGUMENTO_TITLE = "TITLE";
    public static final String ARGUMENTO_FULL_SNIPPET = "FULL_SNIPPET";
    public static final String ARGUMENTO_URL = "URL";
    public static final String ARGUMENTO_RATE = "RATE";
    public static final String ARGUMENTO_LAT = "LATITUD";
    public static final String ARGUMENTO_LON = "LONGITUD";

    private String title, full_snippet, url;
    private double lat, lon, rate;
    private TextView info, description, ratingNum;
    private ImageView placeImage;
    private Button btnRate;
    private int score = 1;
    private BubbleSeekBar scoreSeek;
    private RatingBar rateBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public static InfoMapDialog newInstance(String title, String full_snippet, double rating, String url, double lat, double lon){
        InfoMapDialog fragment = new InfoMapDialog();
        Bundle bundle = new Bundle();
        bundle.putDouble(ARGUMENTO_LAT, lat);
        bundle.putDouble(ARGUMENTO_LON, lon);
        bundle.putString(ARGUMENTO_TITLE, title);
        bundle.putString(ARGUMENTO_FULL_SNIPPET, full_snippet);
        bundle.putDouble(ARGUMENTO_RATE, rating);
        bundle.putString(ARGUMENTO_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        title = args.getString(ARGUMENTO_TITLE);
        full_snippet = args.getString(ARGUMENTO_FULL_SNIPPET);
        rate = args.getDouble(ARGUMENTO_RATE);
        url = args.getString(ARGUMENTO_URL);
        lat = args.getDouble(ARGUMENTO_LAT);
        lon = args.getDouble(ARGUMENTO_LON);

        //image = args.getInt(String.valueOf(ARGUMENTO_IMAGE));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.placeDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialoginfomap,null);
        info = v.findViewById(R.id.info);
        description = v.findViewById(R.id.description);
        ratingNum = v.findViewById(R.id.ratingNum);
        placeImage = v.findViewById(R.id.placeImage);
        btnRate = v.findViewById(R.id.btnRate);
        scoreSeek = v.findViewById(R.id.bubbleSeekBar);
        rateBar = v.findViewById(R.id.ratingBar);
        if (!url.equals("")){
            Picasso.get().load(url).resize(670, 600).into(placeImage);
        }
        info.setText(title);
        description.setText(full_snippet);
        DecimalFormat df = new DecimalFormat("#.#");
        ratingNum.setText(df.format(rate));
        rateBar.setRating((float) rate);
        scoreSeek.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                score = progress;
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
                ratePlace();
            }
        });
        builder.setView(v);
        return builder.create();
    }

    public void refreshFragment() {
        Fragment frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (frg instanceof FragmentMap) {
            FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.detach(frg);
            fragmentTransaction.attach(frg);
            fragmentTransaction.commit();
        }
    }

    public void setRating(List<PersonRating> list){
        double average = 0;
        for (int i = 0; i < list.size(); i++){
            average += list.get(i).getRating();
        }
        average /= list.size();
        DecimalFormat df = new DecimalFormat("#.#");
        ratingNum.setText(df.format(average));
        rateBar.setRating((float) average);
    }

    public void ratePlace(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Places");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<PlaceInformation>> t = new GenericTypeIndicator<List<PlaceInformation>>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                };
                List<PlaceInformation> lugares = dataSnapshot.getValue(t);
                for (int i = 0; i < lugares.size(); i++){
                    if (lugares.get(i).getLatitude() == lat && lugares.get(i).getLongitude() == lon){
                        if (lugares.get(i).getPersonRatings() != null){
                            List<PersonRating> personRatingList = lugares.get(i).getPersonRatings();
                            boolean canRate = true;
                            for (int j = 0; j < personRatingList.size(); j++){
                                if (personRatingList.get(j).getUID().equals(user.getUid())){
                                    canRate = false;

                                }
                            }
                            if (canRate){
                                personRatingList.add(new PersonRating(score, user.getUid()));
                                lugares.get(i).setPersonRatings(personRatingList);
                                //aqui se modifica la calificacion
                                setRating(personRatingList);
                                Toast.makeText(getActivity(), "Score added!", Toast.LENGTH_SHORT).show();
                                refreshFragment();
                                break;
                            } else{
                                Toast.makeText(getActivity(), "You can only vote for a place one.", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        } else{
                            List<PersonRating> personRatingList = new ArrayList<>();
                            personRatingList.add(new PersonRating(score, user.getUid()));
                            lugares.get(i).setPersonRatings(personRatingList);
                            //aqui se modifica la calificacion
                            setRating(personRatingList);
                            Toast.makeText(getActivity(), "Score added!", Toast.LENGTH_SHORT).show();
                            refreshFragment();
                            break;
                        }

                    }
                }

                dataSnapshot.getRef().setValue(lugares);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}