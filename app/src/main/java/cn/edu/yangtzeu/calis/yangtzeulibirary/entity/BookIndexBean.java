package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;


public class BookIndexBean {
    private String categoryDesc;
    private String childrenCount;
    private String libcode;
    private String id;
    private String name;
    private String parentId;
    private String showOrder;

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(String childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getLibcode() {
        return libcode;
    }

    public void setLibcode(String libcode) {
        this.libcode = libcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(String showOrder) {
        this.showOrder = showOrder;
    }
}
