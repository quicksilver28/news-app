package abnd.networking.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class NewsQueryUtil {

    private static final String LOG_TAG = NewsQueryUtil.class.getSimpleName();

    private NewsQueryUtil() {

    }

    public static List<NewsArticleData> fetchNewsDataList(String urlRequest) {
        if (TextUtils.isEmpty(urlRequest)) {
            return null;
        }
        URL url = createUrl(urlRequest);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in closing input stream. ", e);
        }

        List<NewsArticleData> newsDataList = extractFromJson(jsonResponse);
        return newsDataList;

    }

    private static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error in instantiating URL. ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = generateJSONResponse(inputStream);
            } else {
                Log.e(LOG_TAG, "Error in establishing connection.\n Error code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in generating JSON response. ", e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return jsonResponse;
    }

    private static String generateJSONResponse(InputStream inputStream) throws IOException {
        StringBuilder jsonRead = new StringBuilder();
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonRead.append(line);
            }
        }
        return jsonRead.toString();
    }

    private static List<NewsArticleData> extractFromJson(String jsonResponse) {

        List<NewsArticleData> newsDataList = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)) {
            return newsDataList;
        }

        try {

            JSONObject root = new JSONObject(jsonResponse);
            JSONObject responseObject = root.getJSONObject("response");
            JSONArray resultsArray = responseObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject currentResult = resultsArray.getJSONObject(i);

                String title = currentResult.optString("webTitle");
                String section = currentResult.optString("sectionName");
                JSONObject fieldsObject = currentResult.getJSONObject("fields");
                String thumbnail = fieldsObject.optString("thumbnail");
                String wordCount = fieldsObject.optString("wordcount");
                String publishedDate = currentResult.optString("webPublicationDate");
                JSONArray tags = currentResult.getJSONArray("tags");

                String author = "";

                if(tags.length() == 1){
                    JSONObject subTagObject = tags.getJSONObject(0);
                    author = subTagObject.optString("webTitle");
                }

                String webUrl = currentResult.optString("webUrl");

                newsDataList.add(new NewsArticleData(title, section, thumbnail, author, publishedDate.substring(0, 10), webUrl, wordCount));

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error in retrieving JSON Object. ", e);
        }

        return newsDataList;

    }

}
