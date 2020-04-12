package com.example.aplicacioncitaconmemin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Home extends AppCompatActivity {
    MeowBottomNavigation meo;
    private final static int ID_HOME=1;
    private final static int ID_LOGIN=2;
    private final static int ID_ABOUT=3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        meo=(MeowBottomNavigation)findViewById(R.id.bottom_nav);

        meo.add(new MeowBottomNavigation.Model(2,R.drawable.home));
        meo.add(new MeowBottomNavigation.Model(1,R.drawable.login));
        meo.add(new MeowBottomNavigation.Model(3,R.drawable.about));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(),"Clicked item"+item.getId(),Toast.LENGTH_SHORT).show();
                if(item.getId() == ID_HOME ){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
                    Log.wtf("Abri", "Abri el home");
                }else if(item.getId() == ID_LOGIN){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentLogIn()).commit();
                    Log.wtf("Abri", "Abri el login");
                }else if(item.getId() == ID_ABOUT){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAbout()).commit();
                    Log.wtf("Abri", "Abri el about");
                }
            }
        });

        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });

        meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });
    }




}