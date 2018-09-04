package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;


public class ToolItemBean {
    private String name;
    private Class<?> context;
    private int bg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getContext() {
        return context;
    }

    public void setContext(Class<?> context) {
        this.context = context;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }
}
