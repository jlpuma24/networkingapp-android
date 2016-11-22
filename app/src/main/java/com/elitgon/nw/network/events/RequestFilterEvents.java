package com.elitgon.nw.network.events;

/**
 * Created by Usuario on 26/10/2016.
 */
public class RequestFilterEvents {
    private long page;
    private long offset;

    public RequestFilterEvents(long page, long offset) {
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
