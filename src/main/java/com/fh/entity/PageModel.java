package com.fh.entity;

public class PageModel {

    public PageModel() {

    }
    //��ֵû������ bootstrap-table�Ѿ�����
    private Integer pageNumber=1;
    //��ֵû������ bootstrap-table�Ѿ�����
    private Integer pageSize=10;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
