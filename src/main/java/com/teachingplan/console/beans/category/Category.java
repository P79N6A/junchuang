package com.teachingplan.console.beans.category;

import java.util.List;

/**
 * Created by v_yanfzhang on 2018/1/22.
 */
public class Category {

    private int id;

    private String text;

    private int parentId;

    private int type;

    private int subType;

    private String cover;

    private String filePath;

    // 子类型
    private List<Category> nodes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<Category> getNodes() {
        return nodes;
    }

    public void setNodes(List<Category> nodes) {
        this.nodes = nodes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubType() {
        return subType;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
