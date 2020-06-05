package com.example.aplicacioncitaconmemin;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.squareup.picasso.Picasso;
import com.xw.repo.BubbleSeekBar;

public class InfoMapDialog extends AppCompatDialogFragment {

    public static final String ARGUMENTO_TITLE = "TITLE";
    public static final String ARGUMENTO_FULL_SNIPPET = "FULL_SNIPPET";
    public static final String ARGUMENTO_URL = "URL";
    public static final String ARGUMENTO_RATE = "RATE";
    //public static final int ARGUMENTO_IMAGE = 0;

    private String title, full_snippet, rate, url;
    private TextView info, description, ratingNum;
    private ImageView placeImage;
    private Button btnRate;
    private int score = 1;
    private BubbleSeekBar scoreSeek;

    public static InfoMapDialog newInstance(String title, String full_snippet,String rating, String url){
        InfoMapDialog fragment = new InfoMapDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENTO_TITLE, title);
        bundle.putString(ARGUMENTO_FULL_SNIPPET, full_snippet);
        bundle.putString(ARGUMENTO_RATE, rating);
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
        rate = args.getString(ARGUMENTO_RATE);
        url = args.getString(ARGUMENTO_URL);
        //image = args.getInt(String.valueOf(ARGUMENTO_IMAGE));
        Log.wtf("Friends", "SE CREO DIALOG MAPS");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.placeDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialoginfomap,null);

        info = v.findViewById(R.id.info);
        description = v.findViewById(R.id.description);
        ratingNum = v.findViewById(R.id.ratingNum);
        placeImage = v.findViewById(R.id.placeImage);
        btnRate = v.findViewById(R.id.btnRate);
        scoreSeek = v.findViewById(R.id.bubbleSeekBar);

        if (!url.equals("")){
            Picasso.get().load(url).resize(670, 600).into(placeImage);
        }
        info.setText(title);
        description.setText(full_snippet);
        ratingNum.setText(rate);


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
                Log.wtf("Rating", "El rating es: " + score);
            }
        });

        builder.setView(v);

        return builder.create();
    }

}