package com.chaitanya.response;

import java.util.List;

public class PromoCodeResponse {


    /**
     * code_id : 1
     * code : OTF5RB7
     * discount : 50
     * upto : 150
     * minimum : 200
     */

    private List<PromocodesBean> promocodes;

    public List<PromocodesBean> getPromocodes() {
        return promocodes;
    }

    public void setPromocodes(List<PromocodesBean> promocodes) {
        this.promocodes = promocodes;
    }

    public static class PromocodesBean {
        private int code_id;
        private String code;
        private int discount;
        private int upto;
        private int minimum;

        public int getCode_id() {
            return code_id;
        }

        public void setCode_id(int code_id) {
            this.code_id = code_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public int getUpto() {
            return upto;
        }

        public void setUpto(int upto) {
            this.upto = upto;
        }

        public int getMinimum() {
            return minimum;
        }

        public void setMinimum(int minimum) {
            this.minimum = minimum;
        }
    }
}
