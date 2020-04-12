package com.example.aplicacioncitaconmemin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class FragmentHome extends Fragment {

    private EditText title, description, url;
    private Button create, delete, update;

    String[] imageUrls = new String[]{
            "https://picsum.photos/504/600",
            "https://picsum.photos/503/600",
            "https://picsum.photos/502/600",
            "https://picsum.photos/501/600",
            "https://picsum.photos/500/600"
    };


    String[] titles = new String[]{
            "CHOCOLATE",
            "POZOLE",
            "PERRO",
            "BEBECITO",
            "RANDOM"
    };
    String[] descriptions = new String[]{
            "Este es chocolate",
            "Este es pozole",
            "Este es un perro",
            "Este es un bebecito",
            "Random"
    };

    ViewPager viewPager;
    Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        title = v.findViewById(R.id.title);
        description = v.findViewById(R.id.description);
        url = v.findViewById(R.id.url);

        create = v.findViewById(R.id.create);
        update = v.findViewById(R.id.update);
        delete = v.findViewById(R.id.delete);


        adapter = new Adapter(getActivity(),imageUrls,titles,descriptions);

        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        return v;
    }
}
