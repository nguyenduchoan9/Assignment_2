package com.example.nguyendhoang.assignment_2.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyendhoang.assignment_2.Common.Constant;
import com.example.nguyendhoang.assignment_2.R;
import com.example.nguyendhoang.assignment_2.databinding.FragmentArticleDetailBinding;
import com.example.nguyendhoang.assignment_2.model.Article;

public class ArticleDetailFragment extends DialogFragment {

    // TODO: Rename and change types of parameters
    private Article mArticle;
    private FragmentArticleDetailBinding mFragmentArticleDetailBinding;



    public ArticleDetailFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static ArticleDetailFragment newInstance(Article article) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.ARTICLE_DETAIL, article);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticle = getArguments().getParcelable(Constant.ARTICLE_DETAIL);
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentArticleDetailBinding = DataBindingUtil
                .inflate(inflater,R.layout.fragment_article_detail, container, false);
        mFragmentArticleDetailBinding.setArticle(mArticle);
        mFragmentArticleDetailBinding.setArticleDetailFrag(this);
        mFragmentArticleDetailBinding.executePendingBindings();

        return mFragmentArticleDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUpView();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpView() {
        getDialog().setTitle("Article Detail");
    }
}
