package pojo;

import java.util.List;

/**
 * @author Florence
 * 分页实体类（运用泛型提高复用性）
 */
public class Page<T> {
    /**
     *每一页多少文章
     */
    private int pageSize;
    /**
     * 第几页
     */
    private int currentPage;
    /**
     *总共有多少篇文章
     */
    private int totalRecord;
    /**
     * 总的页数
     */
    private int totalPage;
    /**
     * 要展示的内容
     */
    private List<T> dataList;




    public Page(){

    }
    public Page(int pageSize, int currentPage, int totalRecord, int totalPage, List<T> dataList) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalRecord = totalRecord;
        this.totalPage = totalPage;
        this.dataList = dataList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", totalRecord=" + totalRecord +
                ", totalPage=" + totalPage +
                ", dataList=" + dataList +
                '}';
    }
}
