package com.luomo.wechat.po;

import java.util.List;

/**
 * Created by LiuMei on 2016-12-28.
 */
public class NewsMessage extends BaseMessage {

    private int ArticleCount;

    private List<News> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<News> getArticles() {
        return Articles;
    }

    public void setArticles(List<News> articles) {
        Articles = articles;
    }
}
