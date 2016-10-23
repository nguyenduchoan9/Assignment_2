package com.example.nguyendhoang.assignment_2.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by Nguyen.D.Hoang on 10/18/2016.
 */

public class ImageUtil {
    public static void loadImage(ImageView imageView, String urlPath, Context context){
        Picasso.with(context)
                .load(urlPath)
                .into(imageView);
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resource = context.getResources();
        DisplayMetrics metrics = resource.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi/DisplayMetrics.DENSITY_DEFAULT);
    }
}
