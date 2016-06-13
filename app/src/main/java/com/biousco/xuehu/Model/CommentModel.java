package com.biousco.xuehu.Model;

/**
 * Created by Biousco on 6/9.
 */
public class CommentModel {
    public String id;
    public String articleid;
    public String userid;
    public String content;
    public String runtime;
    public String username;
    public String imageurl;

    public CommentModel(String id, String content, String username, String imageurl) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
