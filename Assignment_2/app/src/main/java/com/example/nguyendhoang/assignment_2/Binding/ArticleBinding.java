package com.example.nguyendhoang.assignment_2.Binding;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import com.example.nguyendhoang.assignment_2.R;
import com.example.nguyendhoang.assignment_2.util.BitmapUtil;
import com.example.nguyendhoang.assignment_2.util.IntentUtil;

/**
 * Created by Nguyen.D.Hoang on 10/22/2016.
 */

public class ArticleBinding {
    public ArticleBinding() {
    }

    public void openChromeFromUrl(Context context, String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(context,  R.color.colorChromeTab));
        builder.setStartAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        builder.setExitAnimations(context, android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        builder.enableUrlBarHiding();

        Bitmap bitmap= BitmapUtil.getBitmap(context, R.drawable.ic_share);
        Intent intentShare = IntentUtil.intentShare(url);

        int requestCode = 100;

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                requestCode,
                intentShare,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setActionButton(bitmap, "Share Link", pendingIntent, true);

        CustomTabsIntent customTabsIntent = builder.build();
// and launch the desired Url with CustomTabsIntent.launchUrl()

        customTabsIntent.launchUrl((Activity) context,Uri.parse(url));
    }
}
