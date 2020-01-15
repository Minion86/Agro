package org.General.util;

import java.util.List;

public abstract class PaginadorUtil {

    private int pageSize;
    private int page;
    private int totalPages;

    public PaginadorUtil(int pageSize) {
        this.pageSize = pageSize;
    }

    public abstract long getItemsCount();

    public abstract List listar();

    public int getPageFirstItem() {
        return getPage() * pageSize;
    }

    public long getPageLastItem() {
        long i = getPageFirstItem() + pageSize - 1;
        long count = getItemsCount() - 1;
        if (i > count) {
            i = count;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    public boolean isHasNextPage() {
        return (getPage() + 1) * pageSize + 1 <= getItemsCount();
    }

    public void nextPage() {
        if (isHasNextPage()) {
            setPage(getPage() + 1);
        }
    }

    public void lastPage() {
        setPage(getTotalPages() - 1);
    }

    public boolean isHasPreviousPage() {
        return getPage() > 0;
    }

    public void previousPage() {
        if (isHasPreviousPage()) {
            setPage(getPage() - 1);
        }
    }

    public void firstPage() {
        setPage(0);
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the totalPages
     */
    public int getTotalPages() {
        Long totalItems = getItemsCount();
        Double total = (Double) (totalItems.doubleValue() % getPageSize());
        if (total == 0D) {
            totalPages = totalItems.intValue() / getPageSize();

        } else {
            totalPages = (totalItems.intValue() / getPageSize()) + 1;
        }
        return totalPages;
    }

    /**
     * @param totalPages the totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
