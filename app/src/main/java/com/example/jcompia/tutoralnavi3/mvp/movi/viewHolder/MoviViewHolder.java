package com.example.jcompia.tutoralnavi3.mvp.movi.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jcompia.tutoralnavi3.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yongbeom on 2018. 5. 12..
 */

public class MoviViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.originalname)
    public TextView originalname;
    @BindView(R.id._id)
    public TextView _id;
    @BindView(R.id.encodests)
    public TextView encodests;
    @BindView(R.id.img)
    public ImageView img;

    @BindView(R.id.download)
    Button download;

    public MoviViewHolder(View itemView) {
        super(itemView);
        originalname = (TextView) itemView.findViewById(R.id.originalname);
        _id = (TextView) itemView.findViewById(R.id._id);
        encodests = (TextView) itemView.findViewById(R.id.encodests);
        img = (ImageView) itemView.findViewById(R.id.img);
        /*
        @OnClick(R.id.download)
        public void onViewClicked() {

        }*/
    }
}
