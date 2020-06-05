package com.example.aplicacioncitaconmemin.Feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.aplicacioncitaconmemin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.MyViewHolder> {
    Context context;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    RequestManager glide;

    public AdapterFeed(Context context, ArrayList<ModelFeed> modelFeedArrayList) {

        this.context = context;
        this.modelFeedArrayList = modelFeedArrayList;
        glide = Glide.with(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ModelFeed modelFeed = modelFeedArrayList.get(position);

        holder.tv_name.setText(modelFeed.getNameF());
        holder.tv_time.setText(modelFeed.getDateF());
        holder.tv_status.setText(modelFeed.getStatusF());


        //glide.load(modelFeed.getProfilePicF()).into(holder.imgView_proPic);
        if (modelFeed.getProfilePicF().equals("")){
            glide.load(R.drawable.charlie_chaplin).into(holder.imgView_proPic);
        } else{
            holder.imgView_postPic.setVisibility(View.VISIBLE);
            Picasso.get().load(modelFeed.getProfilePicF()).resize(45, 45).centerCrop().error(R.drawable.charlie_chaplin).into(holder.imgView_proPic);
        }
        if (modelFeed.getPostPicF().equals("")) {
            holder.imgView_postPic.setVisibility(View.GONE);
        } else {
            holder.imgView_postPic.setVisibility(View.VISIBLE);
            Picasso.get().load(modelFeed.getPostPicF()).resize(960,640).centerCrop().into(holder.imgView_postPic);
            //glide.load(modelFeed.getPostPicF()).into(holder.imgView_postPic);
        }
    }

    @Override
    public int getItemCount() {
        return modelFeedArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_time, tv_status;
        ImageView imgView_proPic, imgView_postPic;

        MyViewHolder(View itemView) {
            super(itemView);

            imgView_proPic = itemView.findViewById(R.id.imgView_proPic);
            imgView_postPic = itemView.findViewById(R.id.imgView_postPic);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_status = itemView.findViewById(R.id.tv_status);
        }
    }
}
