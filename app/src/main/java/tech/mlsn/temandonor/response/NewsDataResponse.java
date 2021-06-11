package tech.mlsn.temandonor.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rzzkan on 10/06/2021.
 */
public class NewsDataResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("news_date")
    @Expose
    private String newsDate;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("filename")
    @Expose
    private String filename;

    public NewsDataResponse(String id, String headline, String description, String content, String newsDate, String path, String filename) {
        this.id = id;
        this.headline = headline;
        this.description = description;
        this.content = content;
        this.newsDate = newsDate;
        this.path = path;
        this.filename = filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
