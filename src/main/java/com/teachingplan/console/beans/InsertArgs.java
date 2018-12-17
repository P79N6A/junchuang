package com.teachingplan.console.beans;

import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/1/25.
 */
public class InsertArgs {

    private String tableName;

    Map<String, Object> params;

    public InsertArgs() {
    }

    /**
     *
     * @param tableName
     * @param params
     */
    public InsertArgs(String tableName, Map<String, Object> params) {

        this.tableName = tableName;
        this.params = params;

    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
