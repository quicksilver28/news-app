package abnd.networking.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsArticleData>> {

    private static final String NEWS_DATA_URL = "https://content.guardianapis.com/search?show-fields=headline,thumbnail,wordcount&show-tags=contributor&api-key=4aefb173-17c6-4118-b43a-615465fff739";
    private static final int NEWS_DATA_LOADER_ID = 1;

    private NewsDataAdapter mNewsDataAdapter;

    private TextView mTvNoContent;
    private ProgressBar mLoadingSpinner;
    private SwipeRefreshLayout mRefreshLayout;

    private LoaderManager mLoaderManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingSpinner = findViewById(R.id.loading_spinner);
        mTvNoContent = findViewById(R.id.tv_no_content);
        mRefreshLayout = findViewById(R.id.refresh_swipe_down);

        buildRecyclerView();

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConnected(MainActivity.this)) {
                    buildRecyclerView();
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }
                mRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.news_data_settings){
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<NewsArticleData>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String topicName = sharedPreferences.getString(getString(R.string.topic_name_key),getString(R.string.topic_name_default));
        String noOfArticles = sharedPreferences.getString(getString(R.string.number_news_articles_key),getString(R.string.number_news_articles_default));
        String orderBy = sharedPreferences.getString(getString(R.string.order_by_key),getString(R.string.order_by_default));

        Uri baseUri = Uri.parse(NEWS_DATA_URL);

        Uri.Builder newUri = baseUri.buildUpon();

        if (!(topicName.trim().equals("")||topicName.trim().equals("headlines"))) {
            newUri.appendQueryParameter("q", topicName);
        }
        newUri.appendQueryParameter("page-size",noOfArticles);
        newUri.appendQueryParameter("order-by",orderBy);

        return new NewsDataLoader(this, newUri.toString());

    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticleData>> loader) {
        mNewsDataAdapter.clear();
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticleData>> loader, List<NewsArticleData> newsDataList) {

        mLoadingSpinner.setVisibility(View.GONE);

        mNewsDataAdapter.clear();
        mNewsDataAdapter.notifyDataSetChanged();

        if (newsDataList != null && !newsDataList.isEmpty()) {
            mNewsDataAdapter.addAll(newsDataList);
            mNewsDataAdapter.notifyDataSetChanged();
        } else {
            mTvNoContent.setText(R.string.no_data_found);
        }

    }

    public void buildRecyclerView() {

        if (isConnected(this)) {

            mTvNoContent.setText("");

            RecyclerView recyclerView = findViewById(R.id.rv_news_data);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layout);

            mNewsDataAdapter = new NewsDataAdapter(this, new ArrayList<NewsArticleData>());
            recyclerView.setAdapter(mNewsDataAdapter);

            mNewsDataAdapter.setCustomClickListener(new NewsDataAdapter.CustomClickListener() {
                @Override
                public void onItemClick(int position) {
                    NewsArticleData newsData = mNewsDataAdapter.getItem(position);
                    Uri newsLink = Uri.parse(newsData.getmWebUrl());
                    Intent openNewsLink = new Intent(Intent.ACTION_VIEW, newsLink);
                    if (openNewsLink.resolveActivity(getPackageManager()) != null) {
                        startActivity(openNewsLink);
                    }
                }
            });

            mLoaderManager = getLoaderManager();
            mLoaderManager.initLoader(NEWS_DATA_LOADER_ID, null, this);
        } else {
            mTvNoContent.setText(R.string.no_internet_connection);
            mLoadingSpinner.setVisibility(View.GONE);
        }

    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
