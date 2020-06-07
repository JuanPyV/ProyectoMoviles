package com.example.aplicacioncitaconmemin.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.aplicacioncitaconmemin.R;
import com.google.android.material.tabs.TabLayout;

import com.example.aplicacioncitaconmemin.Adapters.SectionPagerAdapter;

public class FragmentProfile extends Fragment {

    View myFragment;

    ViewPager viewPager;
    TabLayout tabLayout;


    public FragmentProfile() {
        // Required empty public constructor
    }

    public static FragmentProfile getInstance()    {
        return new FragmentProfile();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_profile, container, false);

        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);

        return myFragment;
    }

    //Call onActivity Create method


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new FragmentPerInfo(),"Profile");
        adapter.addFragment(new FragmentFriendList(), "Friends");
        adapter.addFragment(new FragmentPerInfoEdit(), "Edit profile");
        adapter.addFragment(new FragmentPeople(), "Search Friends");

        viewPager.setAdapter(adapter);
    }
}
