package com.elitgon.nw.network.events;

/**
 * Created by Usuario on 17/09/2016.
 */
public class RequestEvents {
    private long page;
    private long offset;

    public RequestEvents(long page, long offset) {
        this.page = page;
        this.offset = offset;
    }

    public long getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
