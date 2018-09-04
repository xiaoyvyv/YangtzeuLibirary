package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;
import java.util.List;
public class ImgBean {
    private List<ImgResult> result;

    public List<ImgResult> getResult() {
        return result;
    }

    public void setResult(List<ImgResult> result) {
        this.result = result;
    }

    public class ImgResult {
        private String coverlink ;
        public String getCoverlink() {
            return coverlink;
        }
        public void setCoverlink(String coverlink) {
            this.coverlink = coverlink;
        }
    }
}