package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;

public class SearchBean {
    private String q = "";
    private String booktype="";
    private String marcformat="CNMARC";
    private String sortWay="score";
    private String sortOrder="desc";
    private String startPubdate="";
    private String endPubdate="";
    private String curlibcode = "CD";
    private String searchWay = "title";
    private String row = "10";
    private String page = "";
    private String hasholding = "1";

    public String getHasholding() {
        return hasholding;
    }

    public void setHasholding(String hasholding) {
        this.hasholding = hasholding;
    }

    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype;
    }

    public String getMarcformat() {
        return marcformat;
    }

    public void setMarcformat(String marcformat) {
        this.marcformat = marcformat;
    }

    public String getSortWay() {
        return sortWay;
    }

    public void setSortWay(String sortWay) {
        this.sortWay = sortWay;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getStartPubdate() {
        return startPubdate;
    }

    public void setStartPubdate(String startPubdate) {
        this.startPubdate = startPubdate;
    }

    public String getEndPubdate() {
        return endPubdate;
    }

    public void setEndPubdate(String endPubdate) {
        this.endPubdate = endPubdate;
    }

    public String getCurlibcode() {
        return curlibcode;
    }

    public void setCurlibcode(String curlibcode) {
        this.curlibcode = curlibcode;
    }

    public String getSearchWay() {
        return searchWay;
    }

    public void setSearchWay(String searchWay) {
        this.searchWay = searchWay;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getAll() {
        return "?q=" + getQ() + "&booktype=" + getBooktype() + "&marcformat=" + getMarcformat()
                + "&sortWay=" + getSortWay() + "&sortOrder=" + getSortOrder()
                + "&startPubdate=" + getStartPubdate() + "&endPubdate=" + getEndPubdate()
                + getCurlibcode() + "&searchWay=" + getSearchWay() + "&rows="+getRow()+"&hasholding=" + getHasholding()+"&page=" + getPage();
    }
}
