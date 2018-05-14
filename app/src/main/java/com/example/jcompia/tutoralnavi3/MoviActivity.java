package com.example.jcompia.tutoralnavi3;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.jcompia.tutoralnavi3.mvp.movi.adapter.MoviAdapter;
import com.example.jcompia.tutoralnavi3.mvp.movi.imp.IMoveTaskContractor;
import com.example.jcompia.tutoralnavi3.mvp.movi.pregenter.MovePregenter;
import com.example.jcompia.tutoralnavi3.mvp.weather.TaskPresenter;
import com.example.jcompia.tutoralnavi3.mvp.weather.ViewPresenter;
import com.example.jcompia.tutoralnavi3.mvp.weather.imp.ITaskContract;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviActivity extends MainActivity implements IMoveTaskContractor.View {
    private IMoveTaskContractor.Pregenter movePregenter;

    @BindView(R.id.loginId)
    EditText loginId;
    @BindView(R.id.loginPw)
    EditText loginPw;
    @BindView(R.id.loginBtn)
    Button loginBtn;

    @BindView(R.id.moviList)
    RecyclerView moviList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toast.makeText(MoviActivity.this, "MoviActivity!",     Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_movi, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.getMenu().getItem(1).setChecked(true);

        ButterKnife.bind(this);
        init();
    }
    public void init(){




        loginBtn = findViewById(R.id.loginBtn);
        moviList = findViewById(R.id.moviList);
        loginId = findViewById(R.id.loginId);
        loginPw = findViewById(R.id.loginPw);


        // rxjava + mvp1 방식
        ViewPresenter mViewPresenter = new ViewPresenter(loginId);
        TaskPresenter mTaskPresenter = new TaskPresenter(new ITaskContract.View() {
            @Override
            public void showWeather(String weather) {

            }

            @Override
            public void setPresenter(ITaskContract.Presenter presenter) {

            }
        });
        mTaskPresenter.getWeatherObsable().subscribe(mViewPresenter.getWetherObserver());


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map paramMap = new HashMap();
                paramMap.put("loginId",loginId.getText());
                paramMap.put("loginPw",loginPw.getText());

                movePregenter.getMovieList(paramMap);
            }
        });


        MoviAdapter moviAdapter = new MoviAdapter();
        movePregenter = new MovePregenter(moviAdapter);

        //moviList.setLayoutManager(new LinearLayoutManager(this));
        //moviList.setHasFixedSize(true);
        //moviList.setAdapter(moviAdapter);





    }


    @Override
    public void setMoviList() {

    }

    @Override
    public void setLoginResult() {

    }


    @Override
    public void setPresenter(IMoveTaskContractor.Pregenter presenter) {
        this.movePregenter = presenter;
    }

    @OnClick(R.id.loginBtn)
    public void onViewClicked() {

        Map paramMap = new HashMap();
        paramMap.put("loginId",loginId.getText());
        paramMap.put("loginPw",loginPw.getText());

        movePregenter.getMovieList(paramMap);
    }
}
