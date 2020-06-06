package com.example.aplicacioncitaconmemin;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.squareup.picasso.Picasso;
import com.xw.repo.BubbleSeekBar;

public class InfoFriendDialog extends AppCompatDialogFragment {

    public static final String ARGUMENTO_USER = "User";
    public static final String ARGUMENTO_NOMBRE = "Nombre";
    public static final String ARGUMENTO_LNAME = "Apellido";
    public static final String ARGUMENTO_LOCATION = "Ubicacion";
    public static final String ARGUMENTO_AGE = "Edad";
    public static final String ARGUMENTO_URL = "URL";

    private String usuario, nombre, apellido, ubicacion, edad, url;
    private TextView username, name, lname, age, ubic, ratingNum;
    private ImageView placeImage;
    private Button btnRate;
    private double rate = 1;
    private BubbleSeekBar scoreSeek;
    private RatingBar rateBar;

    public static InfoFriendDialog newInstance(String user, String nombre, String apellido, String ubicacion, String edad, String url) {
        InfoFriendDialog fragment = new InfoFriendDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENTO_USER, user);
        bundle.putString(ARGUMENTO_NOMBRE, nombre);
        bundle.putString(ARGUMENTO_LNAME, apellido);
        bundle.putString(ARGUMENTO_LOCATION, ubicacion);
        bundle.putString(ARGUMENTO_AGE, edad);
        bundle.putString(ARGUMENTO_URL, url);
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
        Log.wtf("Friends", "SE CREO DIALOG FRIENDS");
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

        Picasso.get().load(url).error(R.drawable.charlie_chaplin).into(placeImage);
        username.setText(usuario);
        name.setText(nombre);
        lname.setText(apellido);
        age.setText(edad);
        ubic.setText(ubicacion);

        ratingNum.setText(rate + "");
        rateBar.setRating((float) rate);
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
                Log.wtf("rating friend", "Rating: " + rate);
            }
        });

        builder.setView(v);

        return builder.create();
    }

}