package abnd.networking.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

public class NewsDataLoader extends AsyncTaskLoader {

    private String mUrl;

    public NewsDataLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsArticleData> loadInBackground() {

        if (TextUtils.isEmpty(mUrl)) {
            return null;
        }

        List<NewsArticleData> newsDataList = NewsQueryUtil.fetchNewsDataList(mUrl);
        return newsDataList;
    }
}
