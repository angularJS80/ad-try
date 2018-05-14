package com.example.jcompia.tutoralnavi3.mvp.movi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jcompia.tutoralnavi3.R;
import com.example.jcompia.tutoralnavi3.mvp.movi.model.MoviModel;
import com.example.jcompia.tutoralnavi3.mvp.movi.viewHolder.MoviViewHolder;
import com.example.jcompia.tutoralnavi3.rest.adapter.CardAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yongbeom on 2018. 5. 12..
 */

public class MoviAdapter extends RecyclerView.Adapter<MoviViewHolder>{
    List<Map> moviList;
    Context viewContext;
    public MoviAdapter(){
        this.moviList = new ArrayList<Map>();
    }

    public void setMoviList(List<Map> moviList){
        this.moviList.clear();
        this.moviList = moviList;
        notifyDataSetChanged();
    }
    @Override
    public MoviViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.viewContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movi_item, parent, false);
        MoviViewHolder viewHolder = new MoviViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MoviViewHolder holder, int position) {

        Log.d("MoviAdapter","onBindViewHolder");




        Map map= moviList.get(position);

        holder._id.setText( map.get("_id").toString());
        holder.encodests.setText( map.get("filename").toString());
        holder.originalname.setText(map.get("originalname").toString());



    }

    @Override
    public int getItemCount() {
        return moviList.size();
    }
}
