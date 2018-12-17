package com.teachingplan.console.beans;

import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/1/25.
 */
public class SelectArgs {

    private String tableName;

    private int beginIndex;

    private int pageSize;

    Map<String, String> params;

    Map<String, String> excludeParams;

    Map<String,String> equalsParams;

    public SelectArgs() {

    }

    /**
     *
     * @param tableName
     * @param beginIndex
     * @param pageSize
     * @param params
     * @param excludeParams 不包括的条件
     */
    public SelectArgs(String tableName, int beginIndex, int pageSize, Map<String, String> params,Map<String, String> excludeParams) {

        this(tableName,beginIndex,pageSize,params);
        this.excludeParams = excludeParams;

    }

    public SelectArgs(String tableName, int beginIndex, int pageSize, Map<String, String> params,Map<String, String> excludeParams,Map<String,String> equalsParams) {

        this(tableName,beginIndex,pageSize,params,excludeParams);
        this.equalsParams = equalsParams;

    }
    /**
     *
     * @param tableName
     * @param beginIndex
     * @param pageSize
     * @param params
     */
    public SelectArgs(String tableName, int beginIndex, int pageSize, Map<String, String> params) {

        this.tableName = tableName;
        this.beginIndex = beginIndex;
        this.pageSize = pageSize;
        this.params = params;

    }

    public SelectArgs(String tableName, Map<String, String> params, Map<String, String> excludeParams) {
        this.tableName = tableName;
        this.params = params;
        this.excludeParams = excludeParams;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getExcludeParams() {
        return excludeParams;
    }

    public void setExcludeParams(Map<String, String> excludeParams) {
        this.excludeParams = excludeParams;
    }
}
