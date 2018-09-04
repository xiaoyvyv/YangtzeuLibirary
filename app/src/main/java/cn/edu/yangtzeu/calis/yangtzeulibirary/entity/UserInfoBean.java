package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;

import com.blankj.utilcode.util.SPUtils;

public class UserInfoBean {
    private long id;
    private String number;
    private String psd;
    private String canUse;
    private String useData;
    private String isActionReader;
    private String CanIn;
    private String CanInAction;
    private String score;
    private String name;
    private String school;
    private String notPay;
    private String readyPay;
    private String readerKind;

    public UserInfoBean() {
        number = SPUtils.getInstance("UserInfo").getString("Number", "");
        psd = SPUtils.getInstance("UserInfo").getString("Password", "");
        this.id = Long.parseLong(number);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getCanUse() {
        return canUse;
    }

    public void setCanUse(String canUse) {
        this.canUse = canUse;
    }

    public String getUseData() {
        return useData;
    }

    public void setUseData(String useData) {
        this.useData = useData;
    }

    public String getIsActionReader() {
        return isActionReader;
    }

    public void setIsActionReader(String isActionReader) {
        this.isActionReader = isActionReader;
    }

    public String getCanIn() {
        return CanIn;
    }

    public void setCanIn(String canIn) {
        CanIn = canIn;
    }

    public String getCanInAction() {
        return CanInAction;
    }

    public void setCanInAction(String canInAction) {
        CanInAction = canInAction;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getNotPay() {
        return notPay;
    }

    public void setNotPay(String notPay) {
        this.notPay = notPay;
    }

    public String getReadyPay() {
        return readyPay;
    }

    public void setReadyPay(String readyPay) {
        this.readyPay = readyPay;
    }

    public String getReaderKind() {
        return readerKind;
    }

    public void setReaderKind(String readerKind) {
        this.readerKind = readerKind;
    }
}
