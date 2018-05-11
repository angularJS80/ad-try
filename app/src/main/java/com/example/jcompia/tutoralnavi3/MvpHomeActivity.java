package com.example.jcompia.tutoralnavi3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.example.jcompia.tutoralnavi3.mvp.weather.TaskPresenter;
import com.example.jcompia.tutoralnavi3.mvp.weather.ViewPresenter;
import com.example.jcompia.tutoralnavi3.mvp.weather.imp.ITaskContract;


public class MvpHomeActivity extends MainActivity /*뷰때어내기 implements ITaskContract.View*/{
    private static final String TAG = "HomeActivity";

    ITaskContract.Presenter mPresenter;
    TaskPresenter mTaskPresenter;
    ViewPresenter mViewPresenter;
    TextView weatherText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Toast.makeText(MvpHomeActivity.this, "MvpHomeActivity!",     Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_mvp_home, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.getMenu().getItem(1).setChecked(true);
        weatherText = (TextView) this.findViewById(R.id.wv2);
        Log.d(TAG, "##Run helloworld"  );


        // rxjava + mvp 방식
        mTaskPresenter = new TaskPresenter();
        mViewPresenter = new ViewPresenter(weatherText);
        mTaskPresenter.getWeatherObsable().subscribe(mViewPresenter.getWetherObserver());

        // retrofit2 + AsyncTask 방식
        weatherText.setText(mTaskPresenter.getWeather()); //

        //mTaskPresenter = new TaskPresenter(this); 뷰때어내기

        Toast.makeText(MvpHomeActivity.this, "MvpHomeActivity!",     Toast.LENGTH_SHORT).show();


    }

    /* 뷰때어내기
    @Override
    public void showWeather(String weather) {
        Toast.makeText(MvpHomeActivity.this, weather,     Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(ITaskContract.Presenter presenter) {
        mPresenter = presenter;
    }*/
}
