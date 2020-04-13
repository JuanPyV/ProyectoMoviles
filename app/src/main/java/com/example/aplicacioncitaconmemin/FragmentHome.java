package com.example.aplicacioncitaconmemin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    private EditText title, description, url;
    private Button create, delete, update;
    private DatabaseReference databaseReference;


    /*String[] imageUrls = new String[]{
            "https://picsum.photos/504/600",
            "https://picsum.photos/503/600",
            "https://picsum.photos/502/600",
            "https://picsum.photos/501/600",
            "https://picsum.photos/500/600"
    };
    */
    private ArrayList<String> imageUrls;
    private ArrayList<String> titles;
    private ArrayList<String> descriptions;

    /*
    String[] titles = new String[]{
            "CHOCOLATE",
            "POZOLE",
            "PERRO",
            "BEBECITO",
            "RANDOM"
    };
     */


    /*
    String[] descriptions = new String[]{
            "Este es chocolate",
            "Este es pozole",
            "Este es un perro",
            "Este es un bebecito",
            "Random"
    };
     */

    private ViewPager viewPager;
    private Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        imageUrls = new ArrayList<String>();
        titles = new ArrayList<String>();
        descriptions = new ArrayList<String>();

        title = v.findViewById(R.id.title);
        description = v.findViewById(R.id.description);
        url = v.findViewById(R.id.url);

        viewPager = v.findViewById(R.id.viewPager);

        create = v.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });

        update = v.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        delete = v.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();

        loadImages();
        return v;
    }

    private void loadImages(){
        imageUrls.clear();
        titles.clear();
        descriptions.clear();
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Lugares");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objDataSnapshot : dataSnapshot.getChildren()){
                    System.out.println(objDataSnapshot.getKey() + " llave");
                    PlaceInformation placeInformation = objDataSnapshot.getValue(PlaceInformation.class);
                    imageUrls.add(placeInformation.getLink());
                     //null porque????
                    titles.add(placeInformation.getTitle());
                    descriptions.add(placeInformation.getDescription());
                    System.out.println(placeInformation.getLink() + "location");
                    System.out.println(placeInformation.getTitle() + "titulo");
                    System.out.println(placeInformation.getDescription() + "descripcion"); //aparece correcto


                    System.out.println("añadiendo 1 lugar");
                }
                String[] imageUrls2 = imageUrls.toArray(new String[0]);
                String[] titles2 = titles.toArray(new String[0]);
                String[] descriptions2 = descriptions.toArray(new String[0]);
                adapter = new Adapter(getActivity(),imageUrls2,titles2,descriptions2);
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void create(){
        String link2 = url.getText().toString();
        String title2 = title.getText().toString();
        String description2 = description.getText().toString();
        PlaceInformation lugar = new PlaceInformation(link2, title2, description2);
        if (link2 != "" && title2 != "" && description2 != ""){
            databaseReference.child("Lugares").child(title2).setValue(lugar);
            Toast.makeText(getActivity(), "Lugar cargado a base de datos", Toast.LENGTH_SHORT).show();
        }
        loadImages();
    }

}
