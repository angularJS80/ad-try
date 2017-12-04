package com.example.jcompia.tutoralnavi3;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.jcompia.tutoralnavi3.fragment.DashboardFragment;

public class FragmentActivity extends MainActivity {
    Bundle instanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instanceState = savedInstanceState;
        super.onCreate(savedInstanceState);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.getMenu().getItem(1).setChecked(true);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_fragment, contentFrameLayout);



        Fragment fragment = new Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_place,fragment);
        fragmentTransaction.commit();

        fragment = new DashboardFragment();
        switchFragment(fragment );

    }

    public void switchFragment( Fragment fragment) {
        Toast.makeText(FragmentActivity.this, "FragmentActivity switchFragment",     Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace( R.id.fragment_place, fragment );
        fragmentTransaction.commit();
    }
}
