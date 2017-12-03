package com.example.jcompia.tutoralnavi3;

import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.jcompia.tutoralnavi3.fragment.LoginFragment;

public class FragmentActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toast.makeText(FragmentActivity.this, "FragmentActivity!",     Toast.LENGTH_SHORT).show();
        Toast.makeText(FragmentActivity.this, getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_NAME),     Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_fragment, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.getMenu().getItem(1).setChecked(true);

        Fragment fragment = new LoginFragment();
        fragment.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( R.id.fragment_place, fragment );
        fragmentTransaction.commit();

        //setContentView(R.layout.activity_fragment);
    }




}
