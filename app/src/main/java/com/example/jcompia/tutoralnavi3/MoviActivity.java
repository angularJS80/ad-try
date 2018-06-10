package com.example.jcompia.tutoralnavi3;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.jcompia.tutoralnavi3.fragment.GenericAccountService;
import com.example.jcompia.tutoralnavi3.mvp.movi.adapter.MoviAdapter;
import com.example.jcompia.tutoralnavi3.mvp.movi.imp.IMoveTaskContractor;
import com.example.jcompia.tutoralnavi3.mvp.movi.pregenter.MovePregenter;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MoviActivity extends MainActivity implements IMoveTaskContractor.View {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

   /* @Inject
    IMoveTaskContractor.Pregenter presenter;*/
    @Inject
    MovePregenter movePregenter;
    @Inject
    MoviAdapter moviAdapter;
    //private IMoveTaskContractor.Pregenter movePregenter;

    @BindView(R.id.loginId)
    EditText loginId;
    @BindView(R.id.loginPw)
    EditText loginPw;
    @BindView(R.id.loginBtn)
    Button loginBtn;

    @BindView(R.id.moviList)
    RecyclerView moviList;

    SharedPreferences sharedPref;
    AccountManager accountManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = getPreferences(Context.MODE_PRIVATE);

        SharedPreferences settings = getSharedPreferences("moviToken", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("logged", "logged");
        editor.commit();

        String token = settings.getString("logged", "").toString();


        new StyleableToast
                .Builder(MoviActivity.this)
                .text("MoviActivity!"+token)
                .textColor(Color.WHITE)
                //.iconStart(R.id.button_login_with_activity)
                //.iconEnd(R.id.icon_view)
                .backgroundColor(Color.blue(30))
               .show();


        //Toast.makeText(MoviActivity.this, "MoviActivity!"+token,     Toast.LENGTH_SHORT).show();


        AndroidInjection.inject(MoviActivity.this);// 엑티비티 주입
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_movi, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.getMenu().getItem(1).setChecked(true);

        ButterKnife.bind(this);
        init();


    }

    public void init(){
        accountManager = (AccountManager) this.getSystemService(Context.ACCOUNT_SERVICE);

        moviAdapter = new MoviAdapter();
        //movePregenter = new MovePregenter(moviAdapter);
        movePregenter.setMoviAdapter(moviAdapter);
        movePregenter.setAccountManager(accountManager);
        moviList.setLayoutManager(new LinearLayoutManager(this));
        moviList.setHasFixedSize(true);
        moviList.setAdapter(moviAdapter);

    }


    @Override
    public void setMoviList() {

    }


    @Override
    public void setPresenter(IMoveTaskContractor.Pregenter presenter) {
        this.movePregenter = (MovePregenter)presenter;
    }

    @OnClick(R.id.loginBtn)
    public void onViewClicked() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("username",loginId.getText().toString());
        paramMap.put("password",loginPw.getText().toString());
        movePregenter.postLogin(paramMap);
        //movePregenter.getMovieList(paramMap);
    }
}
