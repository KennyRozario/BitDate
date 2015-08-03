package com.example.kenny.bitdate;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Kenny on 2015-07-26.
 */
public class CardAdapter extends ArrayAdapter<User> {

    CardAdapter(Context ctx, List<User> users) {
        super(ctx, R.layout.card, R.id.name, users);
    }

    @Override
    public CardView getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        CardView v = (CardView) super.getView(position, convertView, parent);
        TextView nameView = (TextView) v.findViewById(R.id.name);
        nameView.setText(user.getFirstName());
        ImageView userPhoto = (ImageView)v.findViewById(R.id.user_photo);
        Picasso.with(getContext()).load(user.getLargePictureURL()).into(userPhoto);
        return v;
    }
}
