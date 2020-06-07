package com.example.aplicacioncitaconmemin.Dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aplicacioncitaconmemin.Fragments.FragmentMap;
import com.example.aplicacioncitaconmemin.AuxiliaryClasses.PlaceInformation;
import com.example.aplicacioncitaconmemin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InfoPlaceDialog extends AppCompatDialogFragment {

    private EditText title;
    private EditText description;
    private EditText lon;
    private EditText lat;
    private EditText snippet;
    private EditText url;
    private Button submit;
    private ImageView close;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialogplace, null);

        title = v.findViewById(R.id.placeTitle);
        description = v.findViewById(R.id.placeDescription);
        lon = v.findViewById(R.id.placeLon);
        lat = v.findViewById(R.id.placeLat);
        snippet = v.findViewById(R.id.placeSnippet);
        url = v.findViewById(R.id.placeUrl);
        submit = v.findViewById(R.id.submit);
        close = v.findViewById(R.id.imageButton2close);

        final AlertDialog dialog = builder.create();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("CloseDialog", "Se cierra el dialog");
                dialog.dismiss();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlace();
            }
        });


        builder.setView(v);

        return builder.create();
    }

    public void addPlace(){
        final String title2 = title.getText().toString();
        final String description2 = description.getText().toString();
        String lon2 = lon.getText().toString();
        String lat2 = lat.getText().toString();
        final String snippet2 = snippet.getText().toString();
        final String imageLink2 = url.getText().toString();
        if (title2.equals("") || description2.equals("") || lon2.equals("") ||
                lat2.equals("") || snippet2.equals("")){
            Toast.makeText(getActivity(), "You have to fill all spaces, only image URL can be blank.", Toast.LENGTH_SHORT).show();
        } else{
            try{
                final double latitude = Double.parseDouble(lat2);
                final double longitude = Double.parseDouble(lon2);
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
                        if (lugares == null){
                            lugares = new ArrayList<>();
                        }
                        PlaceInformation newPlace = new PlaceInformation(latitude, longitude, title2, imageLink2, description2, snippet2);
                        lugares.add(newPlace);
                        dataSnapshot.getRef().setValue(lugares);
                        Toast.makeText(getActivity(), "New place added correctly", Toast.LENGTH_SHORT).show();
                        //refrescar fragmento para mostrar nuevo marker
                        Fragment frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                        if (frg instanceof FragmentMap){
                            FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.detach(frg);
                            fragmentTransaction.attach(frg);
                            fragmentTransaction.commit();
                        }
                        getDialog().dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } catch(NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "La latitud y longitud deben ser números, introduce un dato válido.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}