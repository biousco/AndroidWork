package com.biousco.xuehu.Model;

import android.graphics.Bitmap;

/**
 * Created by Biousco on 6/13.
 */
public class MainListItemData {
    String id,item_user,item_title,item_content_brief,avatar_url;

    public MainListItemData(String id, String item_user, String item_title, String item_content_brief, String avatar_url) {
        super();
        this.id = id;
        this.item_title = item_title;
        this.item_user = item_user;
        this.item_content_brief = item_content_brief;
        this.avatar_url = avatar_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_user() {
        return item_user;
    }

    public void setItem_user(String item_user) {
        this.item_user = item_user;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_content_brief() {
        return item_content_brief;
    }

    public void setItem_content_brief(String item_content_brief) {
        this.item_content_brief = item_content_brief;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
