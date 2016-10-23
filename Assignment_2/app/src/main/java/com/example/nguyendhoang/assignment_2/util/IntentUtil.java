package com.example.nguyendhoang.assignment_2.util;

import android.content.Intent;
import android.text.Html;

/**
 * Created by Nguyen.D.Hoang on 10/22/2016.
 */

public class IntentUtil {

    public static Intent intentShare(String urlShare) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<a href='" + urlShare + "'>link</a>"));
        return shareIntent;
    }
}
