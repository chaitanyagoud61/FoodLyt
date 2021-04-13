package com.chaitanya.response;

import java.util.List;

public class CategoryFoodItemResponse {


    /**
     * rest_id : 24
     * categories : ["one","two","three","four","five"]
     * items : [{"food_id":1,"name":"Vennala Cake 1Kg","image":"https://www.dropbox.com/s/r51yhdu52g6xxi2/vennala.jpg?raw=1","original_price":240,"status":"on","category":"four","price":240,"type":"Non-Veg","recommended":"yes","other_props":[{"name":"Vennala Cake 1/2Kg","original_price":120,"price":120,"status":"on"},{"name":"Vennala Cake 1/4Kg","original_price":60,"price":60,"status":"on"}]},{"food_id":2,"name":"Chocolate Cake 1Kg","image":"https://www.dropbox.com/s/0t34l3mshtzqqzo/chocolate-05NfYsgs.jpg?raw=1","original_price":240,"status":"on","category":"five","price":240,"type":"Non-Veg","recommended":"yes","other_props":[]},{"food_id":3,"name":"Pineapple Cake 1Kg","image":"https://www.dropbox.com/s/lx9rqzzhe6njpjs/pineapple.jpg?raw=1","original_price":240,"status":"on","category":"three","price":240,"type":"Non-Veg","recommended":"yes","other_props":[]},{"food_id":4,"name":"Vennala Cake 1Kg(Egg Less)","image":"https://www.dropbox.com/s/lpbt99huojnva2r/vennala_eggless-ebRfYsgs.jpg?raw=1","original_price":300,"status":"on","category":"two","price":300,"type":"Veg","recommended":"no","other_props":[]},{"food_id":5,"name":"Pineapple Cake 1Kg(Egg Less)","image":"https://www.dropbox.com/s/jhkqolieb05wh77/pineapple_eggless-G4PfYsgs.jpg?raw=1","original_price":300,"status":"on","category":"two","price":300,"type":"Veg","recommended":"no","other_props":[]},{"food_id":6,"name":"Chocolate Cake 1Kg(Egg Less)","image":"https://www.dropbox.com/s/wzh5ahgh5rz7cvu/chco%3Deggless.jpg?raw=1","original_price":300,"status":"on","category":"three","price":300,"type":"Veg","recommended":"no","other_props":[]},{"food_id":7,"name":"Chocolate Pack ","image":"https://www.dropbox.com/s/bi2qf0s11jv4dxp/chcoclate%20pack.jpg?raw=1","original_price":100,"status":"on","category":"four","price":100,"type":"Veg","recommended":"no","other_props":[]},{"food_id":8,"name":"Biscuits ","image":"https://www.dropbox.com/s/3ofqljgvjyw4iae/biscuits-iVMfYsgs.jpg?raw=1","original_price":30,"status":"on","category":"two","price":30,"type":"Veg","recommended":"no","other_props":[]},{"food_id":9,"name":"Candles + Knife ","image":"https://www.dropbox.com/s/1k545r3blt4r3pp/candle%2Bkinfe-AQMfYsgs.jpg?raw=1","original_price":20,"status":"on","category":"five","price":20,"type":"Veg","recommended":"yes","other_props":[]},{"food_id":10,"name":"Flower Candle ","image":"https://www.dropbox.com/s/8xt2w3q6ses61c9/flower_birthday_candles-UeOfYsgs.jpg?raw=1","original_price":40,"status":"on","category":"two","price":40,"type":"Veg","recommended":"no","other_props":[]},{"food_id":11,"name":"Birthday Cap","image":"https://www.dropbox.com/s/78odjw731lyr5ne/cap-sgNfYsgs.jpg?raw=1","original_price":30,"status":"on","category":"three","price":30,"type":"Veg","recommended":"no","other_props":[]},{"food_id":12,"name":"Snow Spray ","image":"https://www.dropbox.com/s/a75v3qiltcwwe1r/snow_apray-ZSPfYsgs.jpg?raw=1","original_price":40,"status":"on","category":"two","price":40,"type":"Veg","recommended":"no","other_props":[]},{"food_id":13,"name":"Dairy Milk Chocolates ","image":"https://www.dropbox.com/s/6yzwpnwao8decne/dairymilk-fIOfYsgs.jpg?raw=1","original_price":80,"status":"on","category":"five","price":80,"type":"Veg","recommended":"no","other_props":[]},{"food_id":14,"name":"Cup Cake(6pc)","image":"https://www.dropbox.com/s/rq6jchrrjexexj4/cup%20cake.jpg?raw=1","original_price":20,"status":"on","category":"one","price":20,"type":"Non-Veg","recommended":"yes","other_props":[]},{"food_id":15,"name":"Chocolate Cup Cake(6pc)","image":"https://www.dropbox.com/s/6l779i765oqygio/chocolate%20cup%20cake.jpg?raw=1","original_price":30,"status":"on","category":"three","price":30,"type":"Non-Veg","recommended":"no","other_props":[]},{"food_id":16,"name":"Plain Cake 250gm","image":"https://www.dropbox.com/s/oji197nfi7rwvzn/cake-uzNfYsgs.jpg?raw=1","original_price":60,"status":"on","category":"one","price":60,"type":"Non-Veg","recommended":"no","other_props":[]},{"food_id":17,"name":"two Curry Puff ","image":"https://www.dropbox.com/s/yji0nendprcapew/two_puff-8xQfYsgs.jpg?raw=1","original_price":10,"status":"on","category":"two","price":10,"type":"Veg","recommended":"yes","other_props":[]},{"food_id":18,"name":"Egg Puff","image":"https://www.dropbox.com/s/i7y82x67qcv2eim/egg_puff-1aOfYsgs.jpg?raw=1","original_price":15,"status":"on","category":"one","price":15,"type":"Non-Veg","recommended":"no","other_props":[]},{"food_id":19,"name":"Panner Puff","image":"https://www.dropbox.com/s/3myvfq2dgk173vw/panner_puff-kcPfYsgs.jpg?raw=1","original_price":20,"status":"on","category":"two","price":20,"type":"Veg","recommended":"no","other_props":[]},{"food_id":20,"name":"Samosa","image":"https://www.dropbox.com/s/oa2uo6sa0z0z5s3/Samosa-PGPfYsgs.jpg?raw=1","original_price":7,"status":"on","category":"two","price":7,"type":"Veg","recommended":"no","other_props":[]},{"food_id":21,"name":"Dil Pasand","image":"https://www.dropbox.com/s/l4vny90c6bvr0x0/dilpasand.jpg?raw=1","original_price":40,"status":"on","category":"four","price":40,"type":"Veg","recommended":"no","other_props":[]},{"food_id":22,"name":"Kalakand 1/2 Kg ","image":"https://www.dropbox.com/s/nhnfdprgpbgiesu/kalakand-ZbRfYsgs.png?raw=1","original_price":200,"status":"on","category":"two","price":200,"type":"Veg","recommended":"no","other_props":[]},{"food_id":23,"name":"Mothichoor Laddu 1/2 Kg","image":"https://www.dropbox.com/s/xw81c618v9o0n4m/Motichoor_Laddu-IfPfYsgs.jpg?raw=1","original_price":100,"status":"on","category":"four","price":100,"type":"Veg","recommended":"no","other_props":[]},{"food_id":24,"name":"Rasugulla 1/2 Kg ","image":"https://www.dropbox.com/s/ri7jrz9pxm1izdz/rasugulla.jpg?raw=1","original_price":120,"status":"on","category":"two","price":120,"type":"Veg","recommended":"yes","other_props":[]},{"food_id":25,"name":"Gulab Jamun 1/2 Kg","image":"https://www.dropbox.com/s/9od8hr1ignz90s3/gulab%20jamun.jpg?raw=1","original_price":120,"status":"on","category":"two","price":120,"type":"Veg","recommended":"no","other_props":[]},{"food_id":26,"name":"Chum Chum Sweet 1/2 Kg","image":"https://www.dropbox.com/s/1tmqstllvt7bkti/chum_chum_sweet-jANfYsgs.jpg?raw=1","original_price":120,"status":"on","category":"two","price":120,"type":"Veg","recommended":"no","other_props":[]},{"food_id":27,"name":"Kakinada Kaja 1/2 Kg ","image":"https://www.dropbox.com/s/6q7hdkhegdre8x3/kakinada%20kaja.jpeg?raw=1","original_price":100,"status":"on","category":"three","price":100,"type":"Veg","recommended":"no","other_props":[]},{"food_id":28,"name":"Channa Kaja 1/2 Kg ","image":"https://www.dropbox.com/s/cmooftj8vqzvwd9/chenna_kaja-XBNfYsgs.jpg?raw=1","original_price":180,"status":"on","category":"two","price":180,"type":"Veg","recommended":"no","other_props":[]},{"food_id":29,"name":"Malai Puri 1/2 Kg ","image":"https://www.dropbox.com/s/we9of15jwmk3d8m/malai_puri-HsPfYsgs.jpg?raw=1","original_price":190,"status":"on","category":"three","price":190,"type":"Veg","recommended":"no","other_props":[]},{"food_id":30,"name":"Kova Rolls 1/2 Kg ","image":"https://www.dropbox.com/s/a7y08pnj6763oyc/kova_rolls-MFOfYsgs.jpg?raw=1","original_price":190,"status":"on","category":"two","price":190,"type":"Veg","recommended":"no","other_props":[]},{"food_id":31,"name":"Neethi Soan Papdi 1/2 Kg ","image":"https://www.dropbox.com/s/bykea5kmn79y8bx/soan_papdi-LgQfYsgs.jpg?raw=1","original_price":180,"status":"on","category":"two","price":180,"type":"Veg","recommended":"no","other_props":[]},{"food_id":32,"name":"Ghee Mysore Pak 1/2 Kg ","image":"https://www.dropbox.com/s/vnb1890ckw67fky/ghee_mysore_pak-vTNfYsgs.jpg?raw=1","original_price":200,"status":"on","category":"two","price":200,"type":"Veg","recommended":"no","other_props":[]},{"food_id":33,"name":"Jangri 1/2 Kg ","image":"https://www.dropbox.com/s/w4boi98cz0ldhfe/Jangri.jpg?raw=1","original_price":100,"status":"on","category":"two","price":100,"type":"Veg","recommended":"no","other_props":[]},{"food_id":34,"name":"Madatha Kaja 1/2 Kg","image":"https://www.dropbox.com/s/z9kq03msetnvj8w/madtha_kaja-IIOfYsgs.jpg?raw=1","original_price":80,"status":"on","category":"two","price":80,"type":"Veg","recommended":"no","other_props":[]},{"food_id":35,"name":"Badusha 1/2 Kg ","image":"https://www.dropbox.com/s/qv8iv9px5kej2tl/badusha-BLMfYsgs.jpg?raw=1","original_price":80,"status":"on","category":"two","price":80,"type":"Veg","recommended":"yes","other_props":[]},{"food_id":36,"name":"Kaju Barfi 1/2 Kg ","image":"https://www.dropbox.com/s/roome0hkmdkygak/kaju%20burfi.gif?raw=1","original_price":360,"status":"on","category":"two","price":360,"type":"Veg","recommended":"no","other_props":[]},{"food_id":37,"name":"2.25ltr Soft Drinks ","image":"https://www.dropbox.com/s/2vlqub0b3fdmbxe/2.25_ltr_drink-toOfYsgs.jpg?raw=1","original_price":90,"status":"on","category":"two","price":90,"type":"Veg","recommended":"no","other_props":[]},{"food_id":38,"name":"Red Bull ","image":"https://www.dropbox.com/s/z4oji4bdpcbglb8/redbull-mwPfYsgs.jpg?raw=1","original_price":115,"status":"on","category":"two","price":115,"type":"Veg","recommended":"no","other_props":[]},{"food_id":39,"name":"BFizz","image":"https://www.dropbox.com/s/05b5umg1m5aoy1k/BFizz.jpg?raw=1","original_price":15,"status":"on","category":"two","price":15,"type":"Veg","recommended":"no","other_props":[]},{"food_id":40,"name":"Badam Milk ","image":"https://www.dropbox.com/s/e41xa9v2z1vqkum/badam_milk-YEMfYsgs.jpg?raw=1","original_price":20,"status":"on","category":"two","price":20,"type":"Veg","recommended":"no","other_props":[]},{"food_id":41,"name":"Rasamalai Cup ","image":"https://www.dropbox.com/s/k2icylq3bs5j1n5/rasamalai.jpg?raw=1","original_price":20,"status":"on","category":"two","price":20,"type":"Veg","recommended":"no","other_props":[]},{"food_id":42,"name":"Juicy Rasgulla 1pc ","image":"https://www.dropbox.com/s/vu4079b3j9q0fxq/jucy%20rasugulla.cms?raw=1","original_price":20,"status":"on","category":"two","price":20,"type":"Veg","recommended":"no","other_props":[]},{"food_id":43,"name":"Raj Bhog 1pc ","image":"https://www.dropbox.com/s/27mhfkrf6moohe7/rajbhog-tKPfYsgs.jpg?raw=1","original_price":20,"status":"on","category":"two","price":20,"type":"Veg","recommended":"no","other_props":[]},{"food_id":44,"name":"Pootharekulu Plain 1pc ","image":"https://www.dropbox.com/s/pr0pmj6t6x7eoke/Pootharekulu_Plain-vDRfYsgs.png?raw=1","original_price":25,"status":"on","category":"five","price":25,"type":"Veg","recommended":"no","other_props":[]},{"food_id":45,"name":"Mixture 1/2 Kg ","image":"https://www.dropbox.com/s/6im8p6fetzakd5t/mixture.jpg?raw=1","original_price":100,"status":"on","category":"two","price":100,"type":"Veg","recommended":"yes","other_props":[]},{"food_id":46,"name":"Agra Mixture 1/2 Kg","image":"https://www.dropbox.com/s/7tyuf1di4y8adyr/agra%20mixture.jpg?raw=1","original_price":100,"status":"on","category":"two","price":100,"type":"Veg","recommended":"no","other_props":[]},{"food_id":47,"name":"Special Mixture 1/2 Kg","image":"https://www.dropbox.com/s/0lssq9dowhksxeq/spl_mixture-vmQfYsgs.jpg?raw=1","original_price":100,"status":"on","category":"four","price":100,"type":"Veg","recommended":"no","other_props":[]},{"food_id":48,"name":"Kaara Poosa 1/2 Kg","image":"https://www.dropbox.com/s/88s6msm8gblfz8d/kaara%20poosa.jpg?raw=1","original_price":100,"status":"on","category":"two","price":100,"type":"Veg","recommended":"no","other_props":[]},{"food_id":49,"name":"Kaara Boondi 1/2 Kg","image":"https://www.dropbox.com/s/ws9dy712t7lu9xy/kara_boondi-h7RfYsgs.png?raw=1","original_price":100,"status":"on","category":"two","price":100,"type":"Veg","recommended":"yes","other_props":[]},{"food_id":50,"name":"Vaam Pakodi 1/2 Kg","image":"https://www.dropbox.com/s/pgds3hdzfncwmmz/onion_pakoda-gbPfYsgs.jpg?raw=1","original_price":100,"status":"on","category":"two","price":100,"type":"Veg","recommended":"no","other_props":[]},{"food_id":51,"name":"Chekodi","image":"https://www.dropbox.com/s/zml29pz3xvlqvqu/Chekodi-irNfYsgs.jpg?raw=1","original_price":100,"status":"on","category":"three","price":100,"type":"Veg","recommended":"no","other_props":[]},{"food_id":52,"name":"Kaju Badam Mixture 1/2 Kg ","image":"https://www.dropbox.com/s/vjlif4etioreaws/kaju_badham_mixture-5COfYsgs.jpg?raw=1","original_price":160,"status":"on","category":"two","price":160,"type":"Veg","recommended":"no","other_props":[]},{"food_id":53,"name":"Priya Pickles 300gm ","image":"https://www.dropbox.com/s/edzqfc4n46vgs74/priya_picles-LkPfYsgs.jpg?raw=1","original_price":90,"status":"on","category":"three","price":90,"type":"Veg","recommended":"no","other_props":[]},{"food_id":54,"name":"Bread + jam ","image":"https://www.dropbox.com/s/bpc0i02f4c8kgxa/bread%2Bjam-qDMfYsgs.jpg?raw=1","original_price":50,"status":"on","category":"two","price":50,"type":"Veg","recommended":"no","other_props":[]}]
     */

    private int rest_id;
    private List<String> categories;
    /**
     * food_id : 1
     * name : Vennala Cake 1Kg
     * image : https://www.dropbox.com/s/r51yhdu52g6xxi2/vennala.jpg?raw=1
     * original_price : 240
     * status : on
     * category : four
     * price : 240
     * type : Non-Veg
     * recommended : yes
     * other_props : [{"name":"Vennala Cake 1/2Kg","original_price":120,"price":120,"status":"on"},{"name":"Vennala Cake 1/4Kg","original_price":60,"price":60,"status":"on"}]
     */

    private List<ItemsBean> items;

    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        String food_id = "";
        String name = "";
        String image = "";
        int original_price = 0;
        String status = "";
        String category = "";
        int price = 0;
        String type = "";
        String recommended = "";
        /**
         * name : Vennala Cake 1/2Kg
         * original_price : 120
         * price : 120
         * status : on
         */

        private List<OtherPropsBean> other_props;

        public ItemsBean(String food_id, String name, String image, int original_price, String status, String category, int price, String type, String recommended, List<OtherPropsBean> other_props) {
            this.food_id = food_id;
            this.name = name;
            this.image = image;
            this.original_price = original_price;
            this.status = status;
            this.category = category;
            this.price = price;
            this.type = type;
            this.recommended = recommended;
            this.other_props = other_props;
        }

        public ItemsBean(String valueOf, String name, String image, int original_price, String status, String category, int price, String type, String recommended) {
        }

        public String getFood_id() {
            return food_id;
        }

        public void setFood_id(String food_id) {
            this.food_id = food_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(int original_price) {
            this.original_price = original_price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRecommended() {
            return recommended;
        }

        public void setRecommended(String recommended) {
            this.recommended = recommended;
        }

        public List<OtherPropsBean> getOther_props() {
            return other_props;
        }

        public void setOther_props(List<OtherPropsBean> other_props) {
            this.other_props = other_props;
        }

        public static class OtherPropsBean {
            private String name;
            private int food_id;
            private int original_price;
            private int price;
            private String status;

            public int getFood_id() {
                return food_id;
            }

            public void setFood_id(int food_id) {
                this.food_id = food_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOriginal_price() {
                return original_price;
            }

            public void setOriginal_price(int original_price) {
                this.original_price = original_price;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
