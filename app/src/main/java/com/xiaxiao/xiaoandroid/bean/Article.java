package com.xiaxiao.xiaoandroid.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiaxiao on 2017/4/18.
 */

public class Article extends BmobObject {
    String name;
    String url;
    String publishTime;
    MyUser writer;
    String contents;
    String mainContent;
    /**
     * 0 还没有源数据
     * 1 正常 有源数据
     * 2 更新完成 且正确
     * 3 待修复   表示暂时不能 被加载展示
     *
     */
    int havecontent;



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyUser getWriter() {
        return writer;
    }

    public void setWriter(MyUser writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getHavecontent() {
        return havecontent;
    }

    public void setHavecontent(int havecontent) {
        this.havecontent = havecontent;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}
