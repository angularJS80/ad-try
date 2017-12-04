package com.example.jcompia.tutoralnavi3.rest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jcompia.tutoralnavi3.R;
import com.example.jcompia.tutoralnavi3.fragment.ImageLoaderTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    List<Map> mItems;

    public CardAdapter() {
        super();
        mItems = new ArrayList<Map>();
    }
    public void setAraay(ArrayList<Map> arrayList) {
        mItems.clear();
        mItems =arrayList;
        notifyDataSetChanged();
    }


    public void addData(Map map) {
        mItems.add(map);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Map map= mItems.get(i);

        ImageLoaderTask imageLoaderTask = new ImageLoaderTask(
                viewHolder.img,
                "http://www.fnordware.com/superpng/pnggrad8rgb.png"
        );
        imageLoaderTask.execute();
        viewHolder.login.setText(map.get("id").toString());
        viewHolder.repos.setText("repos: " + map.get("name").toString());
        viewHolder.blog.setText("blog: " + map.get("name").toString());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView login;
        public TextView repos;
        public TextView blog;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            repos = (TextView) itemView.findViewById(R.id.repos);
            blog = (TextView) itemView.findViewById(R.id.blog);
            img= (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
