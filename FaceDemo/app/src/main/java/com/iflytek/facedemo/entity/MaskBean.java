package com.iflytek.facedemo.entity;

/**
 * Created by xianshang.liu on 2017/7/10.
 */

public class MaskBean {


    /**
     * type : 1
     * version : 1
     * platform : android
     * isReady : false
     * head : {"imgCount":12,"willShow":true,"scale":4.5,"offset":3.5,"hOffset":0.5,"wOffset":0.5,"index":0}
     * eye : {"willShow":true,"imgCount":12,"scale":4.5,"hOffset":0.5,"wOffset":0.5,"index":0}
     * nose : {"willShow":true,"imgCount":12,"scale":4.5,"hOffset":0.5,"wOffset":0.5,"index":0}
     * mouth : {"willShow":true,"imgCount":12,"scale":4.5,"hOffset":0.5,"wOffset":0.5,"index":0}
     * chin : {"willShow":true,"imgCount":12,"scale":4.5,"hOffset":0.5,"wOffset":0.5,"index":0}
     */

    private int type;
    private int version;
    private String platform;
    private boolean isReady;
    private HeadBean head;
    private EyeBean eye;
    private NoseBean nose;
    private MouthBean mouth;
    private ChinBean chin;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public boolean isIsReady() {
        return isReady;
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }

    public HeadBean getHead() {
        return head;
    }

    public void setHead(HeadBean head) {
        this.head = head;
    }

    public EyeBean getEye() {
        return eye;
    }

    public void setEye(EyeBean eye) {
        this.eye = eye;
    }

    public NoseBean getNose() {
        return nose;
    }

    public void setNose(NoseBean nose) {
        this.nose = nose;
    }

    public MouthBean getMouth() {
        return mouth;
    }

    public void setMouth(MouthBean mouth) {
        this.mouth = mouth;
    }

    public ChinBean getChin() {
        return chin;
    }

    public void setChin(ChinBean chin) {
        this.chin = chin;
    }

    public static class HeadBean extends FacePart {
        /**
         * imgCount : 12
         * willShow : true
         * scale : 4.5
         * offset : 3.5
         * hOffset : 0.5
         * wOffset : 0.5
         * index : 0
         */

        private int imgCount;
        private boolean willShow;
        private double scale;
        private double offset;
        private double hOffset;
        private double wOffset;
        private int index;

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public boolean isWillShow() {
            return willShow;
        }

        public void setWillShow(boolean willShow) {
            this.willShow = willShow;
        }

        public double getScale() {
            return scale;
        }

        public void setScale(double scale) {
            this.scale = scale;
        }

        public double getOffset() {
            return offset;
        }

        public void setOffset(double offset) {
            this.offset = offset;
        }

        public double getHOffset() {
            return hOffset;
        }

        public void setHOffset(double hOffset) {
            this.hOffset = hOffset;
        }

        public double getWOffset() {
            return wOffset;
        }

        public void setWOffset(double wOffset) {
            this.wOffset = wOffset;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static class EyeBean extends FacePart{
        /**
         * willShow : true
         * imgCount : 12
         * scale : 4.5
         * hOffset : 0.5
         * wOffset : 0.5
         * index : 0
         */

        private boolean willShow;
        private int imgCount;
        private double scale;
        private double hOffset;
        private double wOffset;
        private int index;

        public boolean isWillShow() {
            return willShow;
        }

        public void setWillShow(boolean willShow) {
            this.willShow = willShow;
        }

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public double getScale() {
            return scale;
        }

        public void setScale(double scale) {
            this.scale = scale;
        }

        public double getHOffset() {
            return hOffset;
        }

        public void setHOffset(double hOffset) {
            this.hOffset = hOffset;
        }

        public double getWOffset() {
            return wOffset;
        }

        public void setWOffset(double wOffset) {
            this.wOffset = wOffset;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static class NoseBean extends FacePart{
        /**
         * willShow : true
         * imgCount : 12
         * scale : 4.5
         * hOffset : 0.5
         * wOffset : 0.5
         * index : 0
         */

        private boolean willShow;
        private int imgCount;
        private double scale;
        private double hOffset;
        private double wOffset;
        private int index;

        public boolean isWillShow() {
            return willShow;
        }

        public void setWillShow(boolean willShow) {
            this.willShow = willShow;
        }

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public double getScale() {
            return scale;
        }

        public void setScale(double scale) {
            this.scale = scale;
        }

        public double getHOffset() {
            return hOffset;
        }

        public void setHOffset(double hOffset) {
            this.hOffset = hOffset;
        }

        public double getWOffset() {
            return wOffset;
        }

        public void setWOffset(double wOffset) {
            this.wOffset = wOffset;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static class MouthBean extends FacePart{
        /**
         * willShow : true
         * imgCount : 12
         * scale : 4.5
         * hOffset : 0.5
         * wOffset : 0.5
         * index : 0
         */

        private boolean willShow;
        private int imgCount;
        private double scale;
        private double hOffset;
        private double wOffset;
        private int index;

        public boolean isWillShow() {
            return willShow;
        }

        public void setWillShow(boolean willShow) {
            this.willShow = willShow;
        }

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public double getScale() {
            return scale;
        }

        public void setScale(double scale) {
            this.scale = scale;
        }

        public double getHOffset() {
            return hOffset;
        }

        public void setHOffset(double hOffset) {
            this.hOffset = hOffset;
        }

        public double getWOffset() {
            return wOffset;
        }

        public void setWOffset(double wOffset) {
            this.wOffset = wOffset;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static class ChinBean extends FacePart{
        /**
         * willShow : true
         * imgCount : 12
         * scale : 4.5
         * hOffset : 0.5
         * wOffset : 0.5
         * index : 0
         */

        private boolean willShow;
        private int imgCount;
        private double scale;
        private double hOffset;
        private double wOffset;
        private int index;

        public boolean isWillShow() {
            return willShow;
        }

        public void setWillShow(boolean willShow) {
            this.willShow = willShow;
        }

        public int getImgCount() {
            return imgCount;
        }

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }

        public double getScale() {
            return scale;
        }

        public void setScale(double scale) {
            this.scale = scale;
        }

        public double getHOffset() {
            return hOffset;
        }

        public void setHOffset(double hOffset) {
            this.hOffset = hOffset;
        }

        public double getWOffset() {
            return wOffset;
        }

        public void setWOffset(double wOffset) {
            this.wOffset = wOffset;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
