package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;

public class BookRenewBean {
    String BookTM;

    String BookName;
    String BookUrl;

    String TimeIn;
    String TimeOut;

    String times;
    String suoShu;
    String lib;
    String kind;

    public String getSuoShu() {
        return suoShu;
    }

    public void setSuoShu(String suoShu) {
        this.suoShu = suoShu;
    }

    public String getLib() {
        return lib;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }


    public String getBookTM() {
        return BookTM;
    }

    public void setBookTM(String bookTM) {
        BookTM = bookTM;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getBookUrl() {
        return BookUrl;
    }

    public void setBookUrl(String bookUrl) {
        BookUrl = bookUrl;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public void setTimeIn(String timeIn) {
        TimeIn = timeIn;
    }

    public String getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(String timeOut) {
        TimeOut = timeOut;
    }
}
