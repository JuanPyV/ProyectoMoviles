package com.example.aplicacioncitaconmemin;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.squareup.picasso.Picasso;

public class InfoFriendDialog extends AppCompatDialogFragment {

    public static final String ARGUMENTO_NOMBRE = "Nombre";
    public static final String ARGUMENTO_LNAME = "Apellido";
    public static final String ARGUMENTO_LOCATION = "Ubicacion";
    public static final String ARGUMENTO_AGE = "Edad";
    public static final String ARGUMENTO_URL = "URL";

    private String nombre, apellido, ubicacion, edad, url;
    private TextView name, lname, age, ubic;
    private ImageView placeImage;

    public static InfoFriendDialog newInstance(String nombre, String apellido, String ubicacion, String edad, String url) {
        InfoFriendDialog fragment = new InfoFriendDialog();
        Bundle bundle = new Bundle();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialoginfofriend, null);

        name = v.findViewById(R.id.name);
        lname = v.findViewById(R.id.lname);
        ubic = v.findViewById(R.id.ubic);
        age = v.findViewById(R.id.age);
        placeImage = v.findViewById(R.id.placeImage);

        Picasso.get().load(url).into(placeImage);
        name.setText(nombre);
        lname.setText(apellido);
        age.setText(edad);
        ubic.setText(ubicacion);

        builder.setView(v).setTitle(nombre);

        return builder.create();
    }

}