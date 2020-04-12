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

public class Adapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] imageUrls;
    private String[] titles;
    private String[] descriptions;

    Adapter(Context context, String[] imageUrls, String[] titles, String[] descriptions){
        this.context = context;
        this.imageUrls = imageUrls;
        this.titles = titles;
        this.descriptions = descriptions;
    }


    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = layoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item,container,false);
        ImageView imageView;
        TextView title;
        TextView description;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);

        Picasso.get().load(imageUrls[position]).fit().centerCrop().into(imageView);
        title.setText(titles[position]);
        description.setText(descriptions[position]);
        container.addView(view,0);
        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
