package com.astgo.naoxuanfeng.bean;

/**
 * Created by ast009 on 2017/11/30.
 */

public class JpushMessageBean{

    private String id;
    private String title;
    private String desc;
    private String content;
    private String add_time;

    public JpushMessageBean(String id, String title, String content, String add_time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.add_time = add_time;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "JpushMessageBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", content='" + content + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
