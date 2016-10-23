package com.example.nguyendhoang.assignment_2.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyendhoang.assignment_2.Binding.ArticleBinding;
import com.example.nguyendhoang.assignment_2.Common.Constant;
import com.example.nguyendhoang.assignment_2.R;
import com.example.nguyendhoang.assignment_2.databinding.ItemArticleBinding;
import com.example.nguyendhoang.assignment_2.databinding.ItemArticleNoImageBinding;
import com.example.nguyendhoang.assignment_2.model.Article;
import com.example.nguyendhoang.assignment_2.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nguyen.D.Hoang on 10/18/2016.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> mArticles;

    public List<Article> getmArticles() {
        return mArticles;
    }

    private Listener mListener;

    public ArticleAdapter() {
        this.mArticles = new ArrayList<>();
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_article, parent, false);
                return new ViewHolder(itemView);
            case 0:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_article_no_image, parent, false);
                return new NoImageViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        if (holder instanceof ViewHolder) {
            bindViewHolder(article, (ViewHolder) holder);
        } else {
            bindNoImageViewHolder(article, (NoImageViewHolder) holder);
        }
        if (position == mArticles.size() - 1 && mListener != null) {
            mListener.onLoadMore();
        }
    }

    private void bindNoImageViewHolder(Article article, NoImageViewHolder holder) {
       holder.getBinding().setModel(article);
        holder.getBinding().executePendingBindings();
    }

    private void bindViewHolder(Article article, ViewHolder holder) {

        Article.Media media = article.getMultiMedias().get(0);
        ViewGroup.LayoutParams layoutParams = holder.getBinding().ivImage.getLayoutParams();
        layoutParams.height = (int) ImageUtil.convertDpToPixel(media.getHeight(), holder.itemView.getContext());
        holder.getBinding().ivImage.setLayoutParams(layoutParams);
        holder.getBinding().setModel(article);
        holder.getBinding().setAdapter(this);
        holder.getBinding().setArticleBinding(new ArticleBinding());
        holder.getBinding().executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    @Override
    public int getItemViewType(int position) {
        Article article = mArticles.get(position);
        int type = article.isHaveImage() ? Constant.HAVE_IMAGE : Constant.NO_IMAGE;
        return type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemArticleBinding binding;

        public ItemArticleBinding getBinding() {
            return binding;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
//            ButterKnife.bind(this, itemView);
        }
    }

    public class NoImageViewHolder extends RecyclerView.ViewHolder {
        private ItemArticleNoImageBinding binding;

        public ItemArticleNoImageBinding getBinding() {
            return binding;
        }

        public NoImageViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public void setArticle(List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }

    public void addArticle(List<Article> articles) {
        int startPos = mArticles.size();
        mArticles.addAll(articles);
        notifyItemRangeInserted(startPos, articles.size());

    }

    public interface Listener {
        void onLoadMore();
    }

//    public void setOnImageClickListener(Article article){
//        FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
//        ArticleDetailFragment articleDetail = ArticleDetailFragment.newInstance(article);
//        articleDetail.show(fragmentManager, "ArticleDetailFragment");
//    }

//
}
