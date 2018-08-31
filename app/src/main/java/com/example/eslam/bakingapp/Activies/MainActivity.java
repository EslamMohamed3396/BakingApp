package com.example.eslam.bakingapp.Activies;

import android.support.v4.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.eslam.bakingapp.Fragments.Fragment_Fav.FavoriteFragment;
import com.example.eslam.bakingapp.Fragments.Recipe_Fragment;
import com.example.eslam.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;
    private Fragment mFragment = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    int id = item.getItemId();
                    switch (id) {
                        case R.id.home:
                            mFragment = new Recipe_Fragment();
                            setFragment(mFragment);
                            return true;
                        case R.id.fav:
                            mFragment = new FavoriteFragment();
                            setFragment(mFragment);
                        default:
                            return false;
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragment = new Recipe_Fragment();
        setFragment(mFragment);
        ButterKnife.bind(this);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setFragment(Fragment mFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_framea, mFragment);
        transaction.commit();
    }
}
