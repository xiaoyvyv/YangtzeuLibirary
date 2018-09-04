package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;

import java.util.List;

public class NewsListBean {


    /**
     * status : 200
     * data : {"pageNo":2,"pageCount":20,"totalPage":3,"totalCount":56,"resultList":[{"newsId":221,"title":"玩转\u201c云图书馆\u201d\u2014 电子书轻松读","createDate":"20180126","source":"云图书馆平台","image":"images/newsImages/201801/1516949720426.jpg"},{"newsId":220,"title":"您有一份悦己书单，请查收！","createDate":"20180125","source":"云图书馆平台","image":"images/newsImages/201801/1516873302986.jpg"},{"newsId":214,"title":"玩转\u201c云图书馆\u201d\u2014 以书会友，速来！","createDate":"20180119","source":"云图书馆平台","image":"images/newsImages/201801/1516330851275.jpeg"},{"newsId":210,"title":"追随《无问西东》，探秘西南联大图书馆","createDate":"20180117","source":"云图书馆平台","image":"images/newsImages/201801/1516174801773.jpg"}]}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pageNo : 2
         * pageCount : 20
         * totalPage : 3
         * totalCount : 56
         * resultList : [{"newsId":221,"title":"玩转\u201c云图书馆\u201d\u2014 电子书轻松读","createDate":"20180126","source":"云图书馆平台","image":"images/newsImages/201801/1516949720426.jpg"},{"newsId":220,"title":"您有一份悦己书单，请查收！","createDate":"20180125","source":"云图书馆平台","image":"images/newsImages/201801/1516873302986.jpg"},{"newsId":214,"title":"玩转\u201c云图书馆\u201d\u2014 以书会友，速来！","createDate":"20180119","source":"云图书馆平台","image":"images/newsImages/201801/1516330851275.jpeg"},{"newsId":210,"title":"追随《无问西东》，探秘西南联大图书馆","createDate":"20180117","source":"云图书馆平台","image":"images/newsImages/201801/1516174801773.jpg"}]
         */

        private int pageNo;
        private int pageCount;
        private int totalPage;
        private int totalCount;
        private List<ResultListBean> resultList;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * newsId : 221
             * title : 玩转“云图书馆”— 电子书轻松读
             * createDate : 20180126
             * source : 云图书馆平台
             * image : images/newsImages/201801/1516949720426.jpg
             */

            private int newsId;
            private String title;
            private String createDate;
            private String source;
            private String image;

            public int getNewsId() {
                return newsId;
            }

            public void setNewsId(int newsId) {
                this.newsId = newsId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
