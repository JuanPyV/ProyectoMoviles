package com.example.aplicacioncitaconmemin;


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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class InfoPlaceDialog extends AppCompatDialogFragment {

    private EditText title;
    private EditText description;
    private EditText lon;
    private EditText lat;
    private EditText snippet;
    private EditText url;
    private Button submit;
    private ImageButton close;

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
        close = v.findViewById(R.id.close);

        final AlertDialog dialog = builder.create();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("CloseDialog", "Se cierra el dialog");
                dialog.dismiss();
            }
        });


        builder.setView(v);

        return builder.create();
    }

}