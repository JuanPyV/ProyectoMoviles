package com.example.traverio.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.traverio.R;
import com.example.traverio.AuxiliaryClasses.UserInformation;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FriendAdapter extends ArrayAdapter<UserInformation> {

    private Context mContext;
    private List<UserInformation> people;

    public FriendAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes List<UserInformation> list) {
        super(context, 0 , list);
        mContext = context;
        people = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.row_frienditem,parent,false);
        UserInformation friend = people.get(position);
        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_profile);
        //image.setImageResource(R.drawable.batman);
        try{
            Picasso.get().load(friend.getProfilePicURL() + "").resize(60, 60).centerCrop().error(R.drawable.charlie_chaplin).into(image);
        } catch (IllegalArgumentException e){
            Picasso.get().load(R.drawable.charlie_chaplin).resize(94, 94).centerCrop().into(image);
        }


        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(friend.getUsername());


        return listItem;
    }
}