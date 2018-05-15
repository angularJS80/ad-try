package com.example.jcompia.tutoralnavi3;

import android.nfc.Tag;
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
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

//import com.jakewharton.rxbinding.view.RxView;


public class MvpHomeActivity extends MainActivity implements ITaskContract.View {
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
    @BindView(R.id.settingMapBtn)
    Button settingMapBtn;

    Observable obsable;
    ConnectableObservable cobsable;
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
        mTaskPresenter = new TaskPresenter(this);

        obsable = serverCallBtnObservable((Button) findViewById(R.id.rxJavaTstBtn));
        obsable.debounce(1, TimeUnit.SECONDS);



        obsable.subscribe(wheatehrObserver());

         cobsable = serverCallBtnObservable((Button) findViewById(R.id.rxJavaTstBtn)).publish();
         cobsable.subscribe(otherObserver());
         cobsable.connect();

        Toast.makeText(MvpHomeActivity.this, "MvpHomeActivity!", Toast.LENGTH_SHORT).show();
    }

    /*서버통신관련한 버튼에 대한 공통정의 */
    private Observable serverCallBtnObservable(Button button) {
        Observable observable = RxView.clicks(button)
        .observeOn(AndroidSchedulers.mainThread())
                // .throttleFirst(1, TimeUnit.SECONDS)

                /*.map(data -> (
                        Log.d("button Clicked", "button Clicked")
                ))*/;
        return observable;
    }

    private Observer wheatehrObserver() {
        Observer observer = new Observer() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                // rxjava + mvp 방식
                mTaskPresenter.getWeatherObsable().subscribe(mViewPresenter.getWetherObserver());
            }


            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }

        };
        return observer;
    }

    private Observer otherObserver() {
        Observer observer = new Observer() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "otherObserver onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                Toast.makeText(MvpHomeActivity.this, "otherObserver!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "otherObserver onNext");
            }


            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "otherObserver" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "otherObserver");
            }

        };

        return observer;
    }

    private Observer otherObserver2() {
        Observer observer = new Observer() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "otherObserver2 onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                Toast.makeText(MvpHomeActivity.this, "otherObserver!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "otherObserver2 onNext");
            }


            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "otherObserver2" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "otherObserver2");
            }

        };
        return observer;
    }


    @Override
    public void showWeather(String weather) {

    }

    @Override
    public void setPresenter(ITaskContract.Presenter presenter) {

    }

    @OnClick(R.id.aSyncTestBtn)
    public void onViewClicked() {

        // retrofit2 + AsyncTask 방식
        resultText.setText(mTaskPresenter.getWeather()); //
    }

    @OnClick(R.id.settingMapBtn)
    public void settingMapBtnClicked() {
        Log.d("settingMapBtnClicked","changed obsable add Map Operater");
        //cobsable.distinct();
        //cobsable.unsubscribeOn(Schedulers.newThread());
        //cobsable.subscribeOn(Schedulers.newThread());
        cobsable = serverCallBtnObservable((Button) findViewById(R.id.rxJavaTstBtn)).publish();
        cobsable.subscribeOn(Schedulers.newThread());
        cobsable.map(
                data->( Log.d(TAG, "settingMapBtnClicked"))
        );
        cobsable.subscribeWith(otherObserver2());
        //cobsable.publish();
        //cobsable.connect();

    }

    public void  showToast(){
        Toast.makeText(MvpHomeActivity.this, "added Map Oper", Toast.LENGTH_SHORT).show();
    }




    /*@OnClick(R.id.rxJavaTstBtn)
    public void onViewClicked() {
        Log.d(TAG, "onViewClicked");
    }*/
}
