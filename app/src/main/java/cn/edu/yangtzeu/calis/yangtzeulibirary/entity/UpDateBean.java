package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;

public class UpDateBean {
    private String newAppVersion;
    private String title;
    private String message;
    private String rightText;
    private String centerText;
    private String leftText;
    private String canClose;
    private String rightUrl;
    private String centerUrl;
    private String leftUrl;

    /**
     * {
     "newAppVersion": "",
     "title": "",
     "message": "",
     "rightText": "",
     "centerText": "",
     "leftText": "",
     "canClose": "",
     "rightUrl": "",
     "centerUrl": "",
     "leftUrl": ""
     * }
     */
    public String getNewAppVersion() {
        return newAppVersion;
    }

    public void setNewAppVersion(String newAppVersion) {
        this.newAppVersion = newAppVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public String getCenterText() {
        return centerText;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public String getCanClose() {
        return canClose;
    }

    public void setCanClose(String canClose) {
        this.canClose = canClose;
    }

    public String getRightUrl() {
        return rightUrl;
    }

    public void setRightUrl(String rightUrl) {
        this.rightUrl = rightUrl;
    }

    public String getCenterUrl() {
        return centerUrl;
    }

    public void setCenterUrl(String centerUrl) {
        this.centerUrl = centerUrl;
    }

    public String getLeftUrl() {
        return leftUrl;
    }

    public void setLeftUrl(String leftUrl) {
        this.leftUrl = leftUrl;
    }
}
