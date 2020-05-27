package com.example.aplicacioncitaconmemin.Feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aplicacioncitaconmemin.R;

import java.util.ArrayList;

public class FragmentFeed extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    AdapterFeed adapterFeed;


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

        populateRecyclerView();

        return v;
    }

    public void populateRecyclerView() {
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
