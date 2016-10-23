package com.example.nguyendhoang.assignment_2.Binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.nguyendhoang.assignment_2.model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nguyen.D.Hoang on 10/21/2016.
 */

public class UIBinding {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, List<Article.Media> multiMedia){
        Picasso.with(imageView.getContext())
                .load(multiMedia.get(0).getUrl())
                .into(imageView);
    }
}
