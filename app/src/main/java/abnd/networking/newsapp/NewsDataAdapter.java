package abnd.networking.newsapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsDataAdapter extends RecyclerView.Adapter<NewsDataAdapter.NewsDataPlaceHolder> {

    private Context mContext;
    private List<NewsArticleData> newsDataList;
    private CustomClickListener customClickListener;

    public NewsDataAdapter(Context mContext, List<NewsArticleData> newsDataList) {
        this.mContext = mContext;
        this.newsDataList = newsDataList;
    }

    public interface CustomClickListener {
        void onItemClick(int position);
    }

    public void setCustomClickListener(CustomClickListener listener) {
        customClickListener = listener;
    }

    public static class NewsDataPlaceHolder extends RecyclerView.ViewHolder {

        public TextView mTvTitle;
        public TextView mTvSection;
        public ImageView mImgThumbnail;
        public TextView mTvPublishedDate;
        public TextView mTvAuthor;

        public NewsDataPlaceHolder(View view, final CustomClickListener listener) {

            super(view);

            mTvTitle = view.findViewById(R.id.tv_title);
            mTvSection = view.findViewById(R.id.tv_section);
            mImgThumbnail = view.findViewById(R.id.img_thumbnail);
            mTvPublishedDate = view.findViewById(R.id.tv_published_date);
            mTvAuthor = view.findViewById(R.id.tv_author);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public NewsDataPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_list, parent, false);
        NewsDataPlaceHolder newsDataPlaceHolder = new NewsDataPlaceHolder(view, customClickListener);
        return newsDataPlaceHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsDataPlaceHolder holder, int position) {
        NewsArticleData newsData = newsDataList.get(position);
        holder.mTvTitle.setText(newsData.getmTitle());
        holder.mTvSection.setText(newsData.getmSection());//convertWordCount(newsData.getmWordCount()));
        Glide.with(mContext).load(Uri.parse(newsData.getmThumbnail())).into(holder.mImgThumbnail);
        holder.mTvPublishedDate.setText(formatDate(newsData.getmPublihedDate()));
        holder.mTvAuthor.setText(newsData.getmAuthor());
    }

    @Override
    public int getItemCount() {
        return newsDataList.size();
    }

    public String convertWordCount(String wordCount){

        int words = Integer.parseInt(wordCount);
        if (words > 0 && words <= 500){
            return "Short";
        }
        else if (words > 500 && words <= 1250){
            return "Medium";
        }
        return "Long";
    }

    public String formatDate(String dateString) {
        String finalFormat = null;
        try {
            Date convertDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            SimpleDateFormat date = new SimpleDateFormat("MMM dd, yyyy");
            finalFormat = date.format(convertDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalFormat;
    }

    public NewsArticleData getItem(int position) {
        return newsDataList.get(position);
    }

    public void clear() {
        newsDataList.clear();
    }

    public void addAll(List<NewsArticleData> newsList) {
        newsDataList.clear();
        newsDataList.addAll(newsList);
    }

}
