package com.sevendegree.pojo;

import java.util.Date;

public class File {
    private Integer id;

    private String fileName;

    private Integer userId;

    private String url;

    private String desc;

    private Date createTime;

    private Date updateTime;

    public File(Integer id, String fileName, Integer userId, String url, String desc, Date createTime, Date updateTime) {
        this.id = id;
        this.fileName = fileName;
        this.userId = userId;
        this.url = url;
        this.desc = desc;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public File() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}