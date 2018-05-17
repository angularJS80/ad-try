package com.example.jcompia.tutoralnavi3;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;
import com.example.jcompia.tutoralnavi3.mvp.weather.TaskPresenter;
import com.example.jcompia.tutoralnavi3.mvp.weather.ViewPresenter;
import com.example.jcompia.tutoralnavi3.mvp.weather.imp.ITaskContract;
import com.jakewharton.rxbinding2.view.RxView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gun0912.tedbottompicker.TedBottomPicker;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

//import com.jakewharton.rxbinding.view.RxView;


public class MvpHomeActivity extends MainActivity implements ITaskContract.View {
    private static final String TAG = "MvpHomeActivity";

    ITaskContract.Presenter mPresenter;
    TaskPresenter mTaskPresenter;
    ViewPresenter mViewPresenter;
    @BindView(R.id.resultText)
    TextView resultText;

    @BindView(R.id.rxAllTesttBtn)
    Button rxAllTesttBtn1;

    @BindView(R.id.aSyncTestBtn)

    Button aSyncTestBtn;
    @BindView(R.id.settingMapBtn)
    Button settingMapBtn;

    Observable wetherObservable;
    Observable<String> obsable;
    Observable<String> addMapBtnObsable;
    ConnectableObservable cobsable;


    Observable<Long> cold;
    PublishSubject<Long> publishSubject;

    PermissionListener permissionListener;

    @BindView(R.id.rxAddMapTestBtn)
    Button rxAddMapTestBtn;
    @BindView(R.id.rxAddSubscriberTestBtn)
    Button rxAddSubscriberTestBtn;
    @BindView(R.id.otherResultText)
    TextView otherResultText;
    @BindView(R.id.rxChangePublishTestBtn)
    Button rxChangePublishTestBtn;
    @BindView(R.id.rxPublishTestBtn)
    Button rxPublishTestBtn;
    @BindView(R.id.rxIntervalPublishTestBtn)
    Button rxIntervalPublishTestBtn;
    @BindView(R.id.imagePickerTestBtn)
    Button imagePickerTestBtn;

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

        wetherObservable = mTaskPresenter.getWeatherObsable();

        obsable = serverCallBtnObservable((Button) findViewById(R.id.rxAllTesttBtn))
                .map(event -> {
                    return "rxAllTesttBtn1 Map left";
                }); // 기존정의된 map 에 대하여 제정의 가능


        obsable.subscribe(wheatehrFrontObserver());


        cobsable = wetherObservable.publish();
        cobsable.subscribeOn(Schedulers.io());
        Toast.makeText(MvpHomeActivity.this, "MvpHomeActivity!", Toast.LENGTH_SHORT).show();


        publishSubject = PublishSubject.create();
        publishSubject.observeOn(AndroidSchedulers.mainThread());
        publishSubject.subscribeOn(AndroidSchedulers.mainThread());

        cold = Observable.interval(1000, TimeUnit.MILLISECONDS);
        cold.observeOn(Schedulers.newThread());
        cold.subscribeOn(AndroidSchedulers.mainThread());
        cold.subscribe(publishSubject);
    }

    /*서버통신관련한 버튼에 대한 공통정의 */
    private Observable serverCallBtnObservable(Button button) {
        Observable<Object> observable = RxView.clicks(button)
                .observeOn(AndroidSchedulers.mainThread())
                .map(event -> {
                    return "All Button Map left";
                });
        // .throttleFirst(1, TimeUnit.SECONDS)

        return observable;
    }

    private Observer wheatehrFrontObserver() {
        Observer observer = new Observer() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "wheatehrObserver onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "wheatehrObserver onSubscribe" + o.toString());

                // rxjava + mvp 방식

                wetherObservable.subscribe(mViewPresenter.getWetherObserver());
                //wetherObservable.subscribe(otherObserver());
            }


            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "wheatehrObserver onError" + e.toString());
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
                Log.d(TAG, "otherObserver onNext" + o);
                String rtnStr = ((WetherSpcnwsInfoServiceVO) o).getResponse().toString();
                Toast.makeText(MvpHomeActivity.this, rtnStr, Toast.LENGTH_SHORT).show();

                otherResultText.setText(rtnStr);

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

    private Observer publishObserver() {
        Observer observer = new Observer() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "publishObserver onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "publishObserver onNext" + o);
                Log.d(TAG, "Thread Name : " + Thread.currentThread().getName());

                //resultText.setText(o.toString());
               /* MvpHomeActivity.this.runOnUiThread(new Runnable() {
                    public void run() {*/

                    /*}
                });*/


            }


            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "publishObserver" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "publishObserver");
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
    public void onASyncTestBtnClicked() {

        // retrofit2 + AsyncTask 방식
        resultText.setText(mTaskPresenter.getWeather()); //
    }

    @OnClick(R.id.settingMapBtn)
    public void settingMapBtnClicked() {
        Log.d("settingMapBtnClicked", "changed addMapBtnObsable add Map Operater");
        addMapBtnObsable = serverCallBtnObservable((Button) findViewById(R.id.rxAddMapTestBtn))
                .debounce(1, TimeUnit.SECONDS)
                .map(event -> {
                    return "rxAddMapTestBtn Map left";
                }); // 기존정의된 map 에 대하여 제정의 가능
        //obsable.debounce(1, TimeUnit.SECONDS);

        addMapBtnObsable.subscribe(wheatehrFrontObserver());

    }

    public void showToast() {
        Toast.makeText(MvpHomeActivity.this, "added Map Oper", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.rxAddSubscriberTestBtn)
    public void onRxAddSubscriberTestBtnClicked() {
        mTaskPresenter.getWeatherObsable().subscribe(otherObserver());
    }

    @OnClick(R.id.rxChangePublishTestBtn)
    public void onRxChangePublishTestBtnClicked() {
        cobsable.subscribeWith(otherObserver());
    }

    @OnClick(R.id.rxPublishTestBtn)
    public void onRxPublishTestBtnClicked() {
        cobsable.subscribeWith(wheatehrFrontObserver());
        cobsable.connect();

    }

    @OnClick(R.id.rxIntervalPublishTestBtn)
    public void onRxIntervalPublishTestBtnClicked() {
        publishSubject.subscribe(publishObserver());
    }

    @OnClick(R.id.imagePickerTestBtn)
    public void onImagePickerTestBtnClicked() {
        PermissionCheckImageSelect();
    }
    public void PermissionCheckImageSelect(){
        Dexter.withActivity(MvpHomeActivity.this)
                .withPermission("android.permission.WRITE_EXTERNAL_STORAGE")
                .withListener(getPermissionListener())
                .check();

    }
    public PermissionListener getPermissionListener() {
        permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                ImageSelect();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        };
        return permissionListener;
    }

    public void ImageSelect(){
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(MvpHomeActivity.this)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected uri
                    }
                })
                .create();

        tedBottomPicker.show(getSupportFragmentManager());
    }

    /*@OnClick(R.id.rxAllTesttBtn1)
    public void onViewClicked() {
        Log.d(TAG, "onViewClicked");
    }*/
}
