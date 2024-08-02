package com.zaprnt.beans.common.es.response;

import java.util.List;

public class ZESResponse<T> {

    private long count;
    private List<T> hits;
    private int page;

    public ZESResponse() {}

    public ZESResponse(long count, List<T> hits, int page) {
        this.count = count;
        this.hits = hits;
        this.page = page;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getHits() {
        return hits;
    }

    public void setHits(List<T> hits) {
        this.hits = hits;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "ZESResponse{" +
                "totalHits=" + count +
                ", hits=" + hits +
                '}';
    }
}

