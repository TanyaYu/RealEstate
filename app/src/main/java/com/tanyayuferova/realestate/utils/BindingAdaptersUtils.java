package com.tanyayuferova.realestate.utils;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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

    @BindingAdapter("doubleValue")
    public static void setDoubleValue(EditText view, double value) {
        view.setText(value == 0.0 ? "" : String.valueOf(value));
    }

    @InverseBindingAdapter(attribute = "doubleValue")
    public static double getDoubleValue(EditText editText) {
        try {
            return Double.parseDouble(editText.getText().toString());
        } catch (NumberFormatException ex){
            return 0.0;
        }
    }

    @BindingAdapter("intValue")
    public static void setIntValue(EditText view, int value) {
        view.setText(value == 0 ? "" : String.valueOf(value));
    }

    @InverseBindingAdapter(attribute = "intValue")
    public static int getIntValue(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException ex){
            return 0;
        }
    }

    @BindingAdapter(value = "doubleValueAttrChanged")
    public static void setDoubleValueListener(EditText editText, final InverseBindingListener listener) {
        if (listener != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    listener.onChange();
                }
            });
        }
    }

    @BindingAdapter(value = "intValueAttrChanged")
    public static void setIntValueListener(EditText editText, final InverseBindingListener listener) {
        if (listener != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    listener.onChange();
                }
            });
        }
    }
}
