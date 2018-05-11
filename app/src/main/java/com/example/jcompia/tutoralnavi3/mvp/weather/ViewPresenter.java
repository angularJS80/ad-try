package com.example.jcompia.tutoralnavi3.mvp.weather;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.example.jcompia.tutoralnavi3.MvpHomeActivity;
import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ViewPresenter {
    private Gson GSON = new Gson();
    private TextView textView;
    public ViewPresenter(TextView textView) {
        this.textView = textView;
    }

    public Observer getWetherObserver(){
        Observer<? super WetherSpcnwsInfoServiceVO> wetherObserver = new Observer<WetherSpcnwsInfoServiceVO>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("wetherObserver", "onSubscribe : "+d.toString() );
            }

            @Override
            public void onNext(WetherSpcnwsInfoServiceVO wetherSpcnwsInfoServiceVO) {
                Log.e("wetherObserver", "onNext : "+wetherSpcnwsInfoServiceVO.toString() );
                textView.setText(GSON.toJson(wetherSpcnwsInfoServiceVO.getResponse().getBody()));

            }

            @Override
            public void onError(Throwable e) {
                Log.e("wetherObserver", "onError : "+e.toString() );
            }

            @Override
            public void onComplete() {
                Log.e("wetherObserver", "onComplete : ");
            }
        };
        return wetherObserver;
    }

}
