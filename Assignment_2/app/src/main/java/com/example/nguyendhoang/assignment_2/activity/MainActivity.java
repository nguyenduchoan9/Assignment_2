package com.example.nguyendhoang.assignment_2.activity;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nguyendhoang.assignment_2.Common.Constant;
import com.example.nguyendhoang.assignment_2.R;
import com.example.nguyendhoang.assignment_2.adapter.ArticleAdapter;
import com.example.nguyendhoang.assignment_2.api.ArticleApi;
import com.example.nguyendhoang.assignment_2.api.SearchRequest;
import com.example.nguyendhoang.assignment_2.databinding.ActivityMainBinding;
import com.example.nguyendhoang.assignment_2.fragment.SearchSettingFragment;
import com.example.nguyendhoang.assignment_2.model.Article;
import com.example.nguyendhoang.assignment_2.model.ArticleSearchResult;
import com.example.nguyendhoang.assignment_2.util.NetworkAndInternetUtil;
import com.example.nguyendhoang.assignment_2.util.RetrofitUltils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import static android.support.v4.view.MenuItemCompat.getActionView;
import static android.support.v4.view.MenuItemCompat.setOnActionExpandListener;

public class MainActivity extends AppCompatActivity {
    private SearchRequest mSearchRequest;
    private ArticleApi mArticleApi;
    private ArticleAdapter mArticleAdapter;
    private SearchView mSearchView;
    private StaggeredGridLayoutManager mLayoutManager;


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupApi();

        setUpViews();
        if(null == savedInstanceState){
            search();
        }else{
            handleRotateOrientation(savedInstanceState);
            handleComplete();
        }

    }

    private void handleRotateOrientation(Bundle savedInstanceState) {
        ArrayList<Article> listData = savedInstanceState.getParcelableArrayList(Constant.SAVE_INSTANCE_ARTICLE);
        mArticleAdapter.setArticle(listData);
        mSearchRequest = savedInstanceState.getParcelable(Constant.SAVE_INSTANCE_SEARCH_REQ);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<Article> listData = (ArrayList<Article>) mArticleAdapter.getmArticles();
        outState.putParcelableArrayList(Constant.SAVE_INSTANCE_ARTICLE, listData);
        outState.putParcelable(Constant.SAVE_INSTANCE_SEARCH_REQ, mSearchRequest);
        super.onSaveInstanceState(outState);
    }

    private void setUpViews() {
        mArticleAdapter = new ArticleAdapter();
        mArticleAdapter.setListener(() -> MainActivity.this.searchMore());
        handleOrientationGrid();
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        binding.rvArticle.setLayoutManager(mLayoutManager);
        binding.rvArticle.setAdapter(mArticleAdapter);

    }

    private void handleOrientationGrid(){
        Configuration configuration = getResources()
                .getConfiguration();

        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else {
            mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }
    }

    private interface Listener {
        void onResult(ArticleSearchResult articleSearchResult);
    }

    private void setupApi() {
        mSearchRequest = new SearchRequest();
        mArticleApi = RetrofitUltils.get().create(ArticleApi.class);
    }

    private void search() {
        mSearchRequest.resetPage();
        binding.pbLoading.setVisibility(View.VISIBLE);

        fetchArticles(articleSearchResult -> {

            mArticleAdapter.setArticle(articleSearchResult.getArticles());
            binding.rvArticle.scrollToPosition(0);
        });
    }

    private void searchMore() {
        mSearchRequest.nextPage();
        binding.pbLoadMore.setVisibility(View.VISIBLE);
        fetchArticles(articleSearchResult -> mArticleAdapter.addArticle(articleSearchResult.getArticles()));
    }

    private void fetchArticles(Listener listener) {
        String url = mArticleApi.getSearch(mSearchRequest.toQuery()).request().url().toString();
        Log.d("Request arcticle", url);

        if(NetworkAndInternetUtil.isNetworkAvailable(MainActivity.this) && NetworkAndInternetUtil.isOnline()){
            mArticleApi.getSearch(mSearchRequest.toQuery()).enqueue(new Callback<ArticleSearchResult>() {
                @Override
                public void onResponse(Call<ArticleSearchResult> call, Response<ArticleSearchResult> response) {
                    Log.e("body", String.valueOf(response.isSuccessful()));
                    if (null != response.body()) {
                        listener.onResult(response.body());
                        handleComplete();
                    } else {
                        Toast.makeText(MainActivity.this, "END", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ArticleSearchResult> call, Throwable t) {
                    Log.e("MainActivity", t.getMessage());
                }
            });
        }else{
            Toast.makeText(MainActivity.this, "Network have problem", Toast.LENGTH_SHORT);
        }


    }

    private void handleComplete() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.pbLoadMore.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        setOnActionExpandListener(menuItem, new OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mSearchRequest.setKeySearch(null);
                search();
                return true;
            }
        });
        setUpSearchView(menuItem);
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpSearchView(MenuItem menuItem) {
        mSearchView = (SearchView) getActionView(menuItem);
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                mSearchRequest.setKeySearch(query.trim());
                search();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sort:
                showSearchSetting();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSearchSetting(){
        FragmentManager fm = getSupportFragmentManager();
        SearchSettingFragment searchSettingFragment = SearchSettingFragment.newInstance(mSearchRequest);
        searchSettingFragment.setSearchSettingListener(searchRequest -> mSearchRequest = searchRequest);
        searchSettingFragment.show(fm, "SearchSetting");
    }
}
