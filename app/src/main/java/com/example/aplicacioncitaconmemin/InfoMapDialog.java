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

public class InfoMapDialog extends AppCompatDialogFragment {

    public static final String ARGUMENTO_TITLE = "TITLE";
    public static final String ARGUMENTO_FULL_SNIPPET = "FULL_SNIPPET";
    public static final String ARGUMENTO_URL = "URL";
    //public static final int ARGUMENTO_IMAGE = 0;

    private String title, full_snippet, url;
    private TextView info;
    private ImageView placeImage;

    public static InfoMapDialog newInstance(String title, String full_snippet, String url){
        InfoMapDialog fragment = new InfoMapDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENTO_TITLE, title);
        bundle.putString(ARGUMENTO_FULL_SNIPPET, full_snippet);
        bundle.putString(ARGUMENTO_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        title = args.getString(ARGUMENTO_TITLE);
        full_snippet = args.getString(ARGUMENTO_FULL_SNIPPET);
        url = args.getString(ARGUMENTO_URL);
        //image = args.getInt(String.valueOf(ARGUMENTO_IMAGE));
        Log.wtf("Friends", "SE CREO DIALOG MAPS");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialoginfomap,null);

        info = v.findViewById(R.id.info);
        placeImage = v.findViewById(R.id.placeImage);

        if (!url.equals("")){
            Picasso.get().load(url).into(placeImage);
        }
        info.setText(full_snippet);

        builder.setView(v).setTitle(title);

        return builder.create();
    }

}