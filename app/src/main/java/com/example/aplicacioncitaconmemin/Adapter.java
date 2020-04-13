package com.example.aplicacioncitaconmemin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> imageUrls;
    private ArrayList<String> titles;
    private ArrayList<String> descriptions;

    private boolean doNotifyDataSetChangedOnce = false;

    Adapter(Context context, ArrayList<String> imageUrls, ArrayList<String> titles, ArrayList<String> descriptions){
        this.context = context;
        this.imageUrls = imageUrls;
        this.titles = titles;
        this.descriptions = descriptions;
    }


    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        notifyDataSetChanged();

        layoutInflater = layoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item,container,false);
        ImageView imageView;
        TextView title;
        TextView description;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);

        Picasso.get().load(imageUrls.get(position)).fit().centerCrop().into(imageView);
        title.setText(titles.get(position));
        description.setText(descriptions.get(position));
        container.addView(view,position);
        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        notifyDataSetChanged();
        container.removeView((View)object);
    }
}
