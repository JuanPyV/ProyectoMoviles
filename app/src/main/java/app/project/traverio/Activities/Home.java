package app.project.traverio.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import app.project.traverio.Fragments.FragmentFeed;
import app.project.traverio.Fragments.FragmentMap;
import app.project.traverio.Fragments.FragmentProfile;
import app.project.traverio.R;

public class Home extends AppCompatActivity {
    MeowBottomNavigation meo;
    private final static int ID_PERINFO = 2;
    private final static int ID_PEOPLE = 3;
    private final static int ID_MAP = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        meo = (MeowBottomNavigation) findViewById(R.id.bottom_nav);

        meo.add(new MeowBottomNavigation.Model(2, R.drawable.avatar));
        meo.add(new MeowBottomNavigation.Model(3, R.drawable.human));
        meo.add(new MeowBottomNavigation.Model(4, R.drawable.mexico));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentFeed()).commit();
        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                if (item.getId() == ID_PERINFO) {
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.exit_to_right, R.anim.enter_from_right).replace(R.id.fragment_container, new FragmentProfile()).commit();
                } else if (item.getId() == ID_PEOPLE) {
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.exit_to_right, R.anim.enter_from_right).replace(R.id.fragment_container, new FragmentFeed()).commit();
                } else if (item.getId() == ID_MAP) {
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.exit_to_right, R.anim.enter_from_right).replace(R.id.fragment_container, new FragmentMap()).commit();
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