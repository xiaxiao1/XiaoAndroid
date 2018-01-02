package com.xiaxiao.xiaoandroid.bean;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiaxiao on 2017/4/18.
 */

public class ArticleInfo extends BmobObject {
    String url;
    String title;
    List<String> datas;
    Article article;
    String contents;

    public ArticleInfo() {
        datas = new CopyOnWriteArrayList<>();
    }
    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void addInfoItem(String object) {
        this.datas.add(object);
    }
    @Override
    public String toString() {
        return "ArticleInfo{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", datas=" + datas +
                '}';
    }
}
