package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;

import java.util.List;

public class DouBanBean {
    /**
     * rating : {"max":10,"numRaters":0,"average":"0.0","min":0}
     * subtitle :
     * author : ["张继英"]
     * pubdate : 2013-1
     * tags : []
     * origin_title :
     * image : https://img3.doubanio.com/view/subject/m/public/s26679363.jpg
     * binding :
     * translator : []
     * catalog :
     * pages : 283
     * images : {"small":"https://img3.doubanio.com/view/subject/s/public/s26679363.jpg","large":"https://img3.doubanio.com/view/subject/l/public/s26679363.jpg","medium":"https://img3.doubanio.com/view/subject/m/public/s26679363.jpg"}
     * alt : https://book.douban.com/subject/21338904/
     * id : 21338904
     * publisher :
     * isbn10 : 7513619239
     * isbn13 : 9787513619233
     * title : 会计学原理
     * url : https://api.douban.com/v2/book/21338904
     * alt_title :
     * author_intro :
     * summary : 张继英主编的《会计学原理(修订版)》在第一版的基础上查缺补漏，信息更新，完善知识点，与时俱进。
     每一章均以一个案例或问题导入，结尾增设了主要概念、基本训练及案例等内容，扩大信息量，增强可读性。
     《会计学原理(修订版)》共十二章，分别介绍会计基本理论，会计核算的专门方法(包括设置会计科目与账户、运用复式记账法、填制和审核会计凭证、登记会计账簿、成本计算、财产清查、编辑会计报表等)，基本经济业务核算(包括制造企业的资金筹集、供应过程、产品生产、产品销售利润形成与分配等)，各种会计核算组织程序，会计工作管理体制等。
     本书可作为高等院校会计学、财务管理学以及其他经济、管理类在校本科生学习会计的启蒙教科书，也可供从事会计、财务管理和其他经济管理人员自学培训之用。
     * price : 32.00元
     */

    private RatingBean rating;
    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;
    private ImagesBean images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private String price;
    private List<String> author;
    private List<?> tags;
    private List<?> translator;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
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

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }

    public List<?> getTranslator() {
        return translator;
    }

    public void setTranslator(List<?> translator) {
        this.translator = translator;
    }

    public static class RatingBean {
        /**
         * max : 10
         * numRaters : 0
         * average : 0.0
         * min : 0
         */

        private int max;
        private int numRaters;
        private String average;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        /**
         * small : https://img3.doubanio.com/view/subject/s/public/s26679363.jpg
         * large : https://img3.doubanio.com/view/subject/l/public/s26679363.jpg
         * medium : https://img3.doubanio.com/view/subject/m/public/s26679363.jpg
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }
}
