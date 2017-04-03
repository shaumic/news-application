package com.app.news;

/**
 * Created by  on 2017/3/12.
 */

public class News {
    String title,content;

    public News(String title, String content){
        this.title = title;
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

}
