package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;

import java.util.List;

public class HomeNewsBean {

    /**
     * status : 200
     * data :
     {"status": 200,
     "data": [{
     "newsId": 490,
     "image": "images/newsImage/1/1/1530260463711-index.jpg",
     "title": "毛主席笔下的祖国山河"
     },{
     "newsId": 473,
     "image": "images/newsImage/5/1/1529544270551-index.jpg",
     "title": "别样的人生路"
     }]}
     * */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * newsId : 490
         * image : images/newsImage/1/1/1530260463711-index.jpg
         * title : 毛主席笔下的祖国山河
         */

        private int newsId;
        private String image;
        private String title;
        private String source;
        private String createDate;

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
