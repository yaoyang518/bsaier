package com.yaoyang.bser.base;

import com.yaoyang.bser.constants.SiteConstants;
import com.yaoyang.bser.util.NumberUtil;

import java.io.Serializable;

/**
 * 分页类
 *
 * @author yaoyang
 * @date 2018-01-24
 */
public class Paging implements Serializable {

    private static final long serialVersionUID = -7928210350544816044L;

    private int pageSize = SiteConstants.DEFAULT_PAGE_SIZE;

    private int pageNumber = 1;

    private int total = 0;

    public Paging(int pageNumber, int pageSize, long total) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = NumberUtil.parseInt(total + "");
        recomputePageNumber();
    }

    public Paging(int pageNumber, int total) {
        this.pageNumber = pageNumber;
        this.total = total;
        recomputePageNumber();
    }

    public Paging(long total) {
        this.total = NumberUtil.parseInt(total + "");
        recomputePageNumber();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        recomputePageNumber();
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setTotal(long total) {
        this.total = NumberUtil.parseInt(total + "");
        recomputePageNumber();
    }

    public int getTotal() {
        return total;
    }

    public boolean isFirstPage() {
        return pageNumber == 1;
    }

    public boolean isLastPage() {
        return pageNumber >= getLastPageNumber();
    }

    public boolean getHasNextPage() {
        return getLastPageNumber() > pageNumber;
    }

    public boolean getHasPreviousPage() {
        return pageNumber > 1;
    }

    public int getLastPageNumber() {
        return total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
    }

    public int getTotalPageNumber() {
        int lastPageNumber = getLastPageNumber();
        return lastPageNumber <= 0 ? 1 : lastPageNumber;
    }

    protected void recomputePageNumber() {
        int lastPageNumber = getLastPageNumber();
        if (pageNumber > lastPageNumber || Integer.MAX_VALUE == pageNumber) {
            if (lastPageNumber <= 0) {
                pageNumber = 1;
            } else {
                pageNumber = lastPageNumber;
            }
        }
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + pageNumber;
        result = PRIME * result + pageSize;
        result = PRIME * result + total;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Paging other = (Paging) obj;
        if (pageNumber != other.pageNumber) {
            return false;
        }
        if (pageSize != other.pageSize) {
            return false;
        }
        if (total != other.total) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Paging [pageSize=" + pageSize + ", pageNumber=" + pageNumber + ", total=" + total + "]";
    }

}
