package com.tanyayuferova.realestate.utils;

import android.content.Context;

import com.tanyayuferova.realestate.R;

import java.text.DecimalFormat;


/**
 * Created by Tanya Yuferova on 1/24/2018.
 */

public final class NumbersFormatUtils {

    /**
     * Gets number description
     * If the number is a multiple of 1000, the result would contain prefix k instead of 000
     * In other cases, the result would contain max 2 fraction digits
     * @param number
     * @param context
     * @return
     */
    public static String numberToShortDescription(Double number, Context context) {
        if(number%1000 == 0)
            // we don't want to display a lot of zeros
            return String.format("%1$,.0f", number/1000) + " " + context.getString(R.string.kilo);
        return new DecimalFormat("#.##").format(number);
    }
}
