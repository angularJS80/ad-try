package com.example.jcompia.tutoralnavi3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jcompia.tutoralnavi3.govweather.ApiConnector;
import com.example.jcompia.tutoralnavi3.govweather.AsyncTaskAPIConnector;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends MainActivity {
    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Toast.makeText(HomeActivity.this, "HomeActivity!",     Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_home, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.getMenu().getItem(1).setChecked(true);

        Log.d(TAG, "##Run helloworld"  );
        ApiConnector.helloworld(HomeActivity.this);
       AsyncTaskAPIConnector asyncTaskAPIConnector = new AsyncTaskAPIConnector(this, (TextView) findViewById(R.id.wv));
        final String serviceKey= "hOoqoTjEflU73a4GVB%2FWraajQopg6BxoSZQ6Ie6OMIBG%2FaUoktc7ep2jDZhsJVFHI62DzbqG7pnPbdPauLuM7g%3D%3D";
        final String fromTmFc = "20171203";
        final String toTmFc = "20171203";
        final int numOfRows = 1;
        final int pageNo = 1;
        final int pageSize = 1;
        final int startPage = 1;
        final String stnId = "108";
        final String _type ="json";

        Map<String, String> map = new HashMap<>();
        map.put("serviceKey", serviceKey);
        map.put("fromTmFc", fromTmFc);
        map.put("toTmFc", toTmFc);
        map.put("numOfRows", numOfRows+"");
        map.put("pageSize", pageSize+"");
        map.put("pageNo", pageNo+"");
        map.put("startPage", startPage+"");
        map.put("stnId", stnId);
        map.put("_type", _type);
        asyncTaskAPIConnector.execute(map);

    }
}
