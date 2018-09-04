package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 * @author 王怀玉
 * @explain 长江大学图书馆的实体类
 */

public class BookWhereBean implements Serializable{
    private List<HoldingList> holdingList;

    public List<HoldingList> getHoldingList() {
        return holdingList;
    }

    public void setHoldingList(List<HoldingList> holdingList) {
        this.holdingList = holdingList;
    }

    public class HoldingList {
        private String state;
        private String barcode;
        private String callno;
        private String orglib;
        private String orglocal;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getCallno() {
            return callno;
        }

        public void setCallno(String callno) {
            this.callno = callno;
        }

        public String getOrglib() {
            return orglib;
        }

        public void setOrglib(String orglib) {
            this.orglib = orglib;
        }

        public String getOrglocal() {
            return orglocal;
        }

        public void setOrglocal(String orglocal) {
            this.orglocal = orglocal;
        }

    }
}
