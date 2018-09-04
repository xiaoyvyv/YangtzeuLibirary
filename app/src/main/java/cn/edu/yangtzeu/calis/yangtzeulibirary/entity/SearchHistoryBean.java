package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;

public class SearchHistoryBean {
    public long id;
    private String url;
    private String title;
    private String isbn;
    private String author;
    private String chuban;
    private String activityType;
    private String bookKind;
    private String bookYear;
    private long recno;
    private long time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChuban() {
        return chuban;
    }

    public void setChuban(String chuban) {
        this.chuban = chuban;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getBookKind() {
        return bookKind;
    }

    public void setBookKind(String bookKind) {
        this.bookKind = bookKind;
    }

    public String getBookYear() {
        return bookYear;
    }

    public void setBookYear(String bookYear) {
        this.bookYear = bookYear;
    }

    public long getRecno() {
        return recno;
    }

    public void setRecno(long recno) {
        this.recno = recno;
    }
}
