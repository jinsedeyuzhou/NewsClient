package com.study.newsclient.entity;

public class PerceptionBean {
    /**
     * inputText : {"text":"附近的酒店"}
     * inputImage : {"url":"imageUrl"}
     * selfInfo : {"location":{"city":"北京","province":"北京","street":"信息路"}}
     */

    private InputTextBean inputText;
    private InputImageBean inputImage;
    private SelfInfoBean selfInfo;

    public InputTextBean getInputText() {
        return inputText;
    }

    public void setInputText(InputTextBean inputText) {
        this.inputText = inputText;
    }

    public InputImageBean getInputImage() {
        return inputImage;
    }

    public void setInputImage(InputImageBean inputImage) {
        this.inputImage = inputImage;
    }

    public SelfInfoBean getSelfInfo() {
        return selfInfo;
    }

    public void setSelfInfo(SelfInfoBean selfInfo) {
        this.selfInfo = selfInfo;
    }

    public static class InputTextBean {
        /**
         * text : 附近的酒店
         */

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class InputImageBean {
        /**
         * url : imageUrl
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class SelfInfoBean {
        /**
         * location : {"city":"北京","province":"北京","street":"信息路"}
         */

        private LocationBean location;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public static class LocationBean {
            /**
             * city : 北京
             * province : 北京
             * street : 信息路
             */

            private String city;
            private String province;
            private String street;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }
        }
    }
}