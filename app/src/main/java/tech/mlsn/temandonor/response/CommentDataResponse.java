package tech.mlsn.temandonor.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rzzkan on 10/06/2021.
 */
public class CommentDataResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("comment_date")
    @Expose
    private String commentDate;
    @SerializedName("comment")
    @Expose
    private String comment;

    public CommentDataResponse(String id, String username, String commentDate, String comment) {
        this.id = id;
        this.username = username;
        this.commentDate = commentDate;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
