package com.tanyayuferova.realestate.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingAdaptersUtils {

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .into(view);
    }

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImageError(ImageView view, String url, Drawable error) {
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(error)
                .error(error)
                .into(view);
    }
}
