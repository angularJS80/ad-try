package com.example.jcompia.tutoralnavi3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jcompia.tutoralnavi3.mvp.weather.TaskPresenter;
import com.example.jcompia.tutoralnavi3.mvp.weather.ViewPresenter;
import com.example.jcompia.tutoralnavi3.mvp.weather.imp.ITaskContract;
//import com.jakewharton.rxbinding.view.RxView;


import butterknife.BindView;
import butterknife.ButterKnife;

//import rx.Observable;
//import rx.Observer;
//import rx.schedulers.Schedulers;


public class MvpHomeActivity extends MainActivity /*뷰때어내기 implements ITaskContract.View*/ {
    private static final String TAG = "HomeActivity";

    ITaskContract.Presenter mPresenter;
    TaskPresenter mTaskPresenter;
    ViewPresenter mViewPresenter;
    @BindView(R.id.resultText)
    TextView resultText;
    @BindView(R.id.rxJavaTstBtn)
    Button rxJavaTstBtn;
    @BindView(R.id.aSyncTestBtn)
    Button aSyncTestBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Toast.makeText(MvpHomeActivity.this, "MvpHomeActivity!", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);


        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        View view = getLayoutInflater().inflate(R.layout.activity_mvp_home, contentFrameLayout);
        ButterKnife.bind(MvpHomeActivity.this, view);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.getMenu().getItem(1).setChecked(true);
        Log.d(TAG, "##Run helloworld");
        init();



    }

    private void init() {

        mTaskPresenter = new TaskPresenter(new ITaskContract.View() {
            @Override
            public void showWeather(String weather) {

            }

            @Override
            public void setPresenter(ITaskContract.Presenter presenter) {

            }
        });
        mViewPresenter = new ViewPresenter(resultText);
        // retrofit2 + AsyncTask 방식
        resultText.setText(mTaskPresenter.getWeather()); //
        //mTaskPresenter = new TaskPresenter(this); 뷰때어내기

        //serverCallBtnObservable(rxJavaTstBtn).subscribe(wheatehrObserver());

        Toast.makeText(MvpHomeActivity.this, "MvpHomeActivity!", Toast.LENGTH_SHORT).show();
    }

    /*서버통신관련한 버튼에 대한 공통정의 */
    /*private Observable serverCallBtnObservable(Button button){
        Observable observable =  RxView.clicks(button)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread());
        return observable;
    }

    private Observer wheatehrObserver(){
        Observer observer = new rx.Observer() {

            @Override
            public void onNext(Object o) {
                // rxjava + mvp 방식
                mTaskPresenter.getWeatherObsable().subscribe(mViewPresenter.getWetherObserver());
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

        };
        return observer;
    }
*/

    /* 뷰때어내기
    @Override
    public void showWeather(String weather) {
        Toast.makeText(MvpHomeActivity.this, weather, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(ITaskContract.Presenter presenter) {
        mPresenter = presenter;
    }*/
}
