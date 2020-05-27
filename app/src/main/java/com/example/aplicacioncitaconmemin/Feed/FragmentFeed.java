package com.example.aplicacioncitaconmemin.Feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aplicacioncitaconmemin.R;
import com.example.aplicacioncitaconmemin.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentFeed extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    AdapterFeed adapterFeed;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapterFeed = new AdapterFeed(getActivity(), modelFeedArrayList);
        recyclerView.setAdapter(adapterFeed);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        populateRecyclerView();

        return v;
    }

    public void addNewPost(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userData = dataSnapshot.child("Users").child(user.getUid()).child("UserInformation").getValue(UserInformation.class);
                java.util.Date date = new java.util.Date();
                ModelFeed newPost = new ModelFeed(userData.getFirstName() + userData.getLastName() , "status", date.toString(),
                        R.drawable.batman, 0);
                GenericTypeIndicator<List<ModelFeed>> t = new GenericTypeIndicator<List<ModelFeed>>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                };
                List<ModelFeed> lista = dataSnapshot.child("Posts").getValue(t);
                if (lista == null){
                    lista = new ArrayList<>();
                    lista.add(newPost);
                } else{
                    lista.add(newPost);
                }
                databaseReference.child("Posts").setValue(lista);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void populateRecyclerView() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Posts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<ModelFeed>> t = new GenericTypeIndicator<List<ModelFeed>>() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                };
                List<ModelFeed> lista = dataSnapshot.getValue(t);
                if (lista != null){
                    for (int i = 0; i < lista.size(); i++){
                        ModelFeed newItem = lista.get(i);
                        modelFeedArrayList.add(newItem);
                    }
                    adapterFeed.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        java.util.Date date=new java.util.Date();
        ModelFeed modelFeed = new ModelFeed("Sajin Maharjan","The cars we drive say a lot about us." , date.toString(),
                R.drawable.batman, R.drawable.img_post1);

        modelFeedArrayList.add(modelFeed);
        modelFeed = new ModelFeed("Karun Shrestha", "Don't be afraid of your fears. They're not there to scare you. They're there to \n" +
                "let you know that something is worth it.", date.toString(), R.drawable.joseph_stalin, 0);

        modelFeedArrayList.add(modelFeed);
        modelFeed = new ModelFeed("Lakshya Ram", "That reflection!!!", date.toString(),
                R.drawable.charlie_chaplin, R.drawable.img_post2);
        modelFeedArrayList.add(modelFeed);

        adapterFeed.notifyDataSetChanged();
    }
}
