package abnd.networking.newsapp;

public class NewsArticleData {

    private String mTitle;  //webTitle
    private String mSection; //sectionName
    private String mThumbnail;  //thumbnail
    private String mAuthor;     //contributor
    private String mPublihedDate;   //webPublicationDate
    private String mWebUrl; //webUrl
    private String mWordCount; //wordcount

    public NewsArticleData(String mTitle, String mSection, String mThumbnail, String mAuthor, String mPublihedDate, String mWebUrl, String mWordCount) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mThumbnail = mThumbnail;
        this.mAuthor = mAuthor;
        this.mPublihedDate = mPublihedDate;
        this.mWebUrl = mWebUrl;
        this.mWordCount = mWordCount;
    }

    public String getmPublihedDate() {
        return mPublihedDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmWebUrl() {
        return mWebUrl;
    }

    public String getmWordCount() {
        return mWordCount;
    }

}
