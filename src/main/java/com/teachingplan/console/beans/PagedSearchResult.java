package com.teachingplan.console.beans;

import java.util.List;
import java.util.Map;

/**
 * Created by justinhuang on 25/04/2017.
 */
public class PagedSearchResult<T> {

    private List<T> rows;

    private int records;

    private int total;

    private int page;

    private Map<String,Object> mapData;

    public PagedSearchResult(int page, int records, int total, List<T> rows) {
        this.rows = rows;
        this.records = records;
        this.total = total;
        this.page = page;
    }

    public PagedSearchResult(int page, int records, int total, List rows, Map mapData) {
        this.rows = rows;
        this.records = records;
        this.total = total;
        this.page = page;
        this.mapData = mapData;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Map<String, Object> getMapData() {
        return mapData;
    }

    public void setMapData(Map<String, Object> mapData) {
        this.mapData = mapData;
    }
}
