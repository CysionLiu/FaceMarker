package com.iflytek.facedemo;

import java.util.List;

/**
 * Created by xianshang.liu on 2017/6/21.
 */

public class FaceData {

    /**
     * face : [{"position":{"top":141,"left":202,"right":395,"bottom":334},"landmark":{"left_eyebrow_left_corner":{"x":393,"y":193},"left_eyebrow_middle":{"x":396,"y":222},"left_eyebrow_right_corner":{"x":386,"y":247},"right_eyebrow_left_corner":{"x":373,"y":290},"right_eyebrow_middle":{"x":371,"y":316},"right_eyebrow_right_corner":{"x":354,"y":338},"left_eye_left_corner":{"x":363,"y":200},"left_eye_right_corner":{"x":355,"y":235},"right_eye_left_corner":{"x":341,"y":285},"right_eye_right_corner":{"x":331,"y":319},"nose_left":{"x":307,"y":222},"nose_bottom":{"x":293,"y":247},"nose_right":{"x":294,"y":273},"mouth_upper_lip_top":{"x":271,"y":242},"mouth_middle":{"x":259,"y":238},"mouth_lower_lip_bottom":{"x":245,"y":235},"left_eye_center":{"x":362,"y":219},"right_eye_center":{"x":339,"y":303},"nose_top":{"x":314,"y":250},"mouth_left_corner":{"x":262,"y":197},"mouth_right_corner":{"x":244,"y":272}}}]
     * ret : 0
     */

    private int ret;
    private List<FaceBean> face;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public List<FaceBean> getFace() {
        return face;
    }

    public void setFace(List<FaceBean> face) {
        this.face = face;
    }

    public static class FaceBean {
        /**
         * position : {"top":141,"left":202,"right":395,"bottom":334}
         * landmark : {"left_eyebrow_left_corner":{"x":393,"y":193},"left_eyebrow_middle":{"x":396,"y":222},"left_eyebrow_right_corner":{"x":386,"y":247},"right_eyebrow_left_corner":{"x":373,"y":290},"right_eyebrow_middle":{"x":371,"y":316},"right_eyebrow_right_corner":{"x":354,"y":338},"left_eye_left_corner":{"x":363,"y":200},"left_eye_right_corner":{"x":355,"y":235},"right_eye_left_corner":{"x":341,"y":285},"right_eye_right_corner":{"x":331,"y":319},"nose_left":{"x":307,"y":222},"nose_bottom":{"x":293,"y":247},"nose_right":{"x":294,"y":273},"mouth_upper_lip_top":{"x":271,"y":242},"mouth_middle":{"x":259,"y":238},"mouth_lower_lip_bottom":{"x":245,"y":235},"left_eye_center":{"x":362,"y":219},"right_eye_center":{"x":339,"y":303},"nose_top":{"x":314,"y":250},"mouth_left_corner":{"x":262,"y":197},"mouth_right_corner":{"x":244,"y":272}}
         */

        private PositionBean position;
        private LandmarkBean landmark;

        public PositionBean getPosition() {
            return position;
        }

        public void setPosition(PositionBean position) {
            this.position = position;
        }

        public LandmarkBean getLandmark() {
            return landmark;
        }

        public void setLandmark(LandmarkBean landmark) {
            this.landmark = landmark;
        }

        public static class PositionBean {
            /**
             * top : 141
             * left : 202
             * right : 395
             * bottom : 334
             */

            private int top;
            private int left;
            private int right;
            private int bottom;

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getRight() {
                return right;
            }

            public void setRight(int right) {
                this.right = right;
            }

            public int getBottom() {
                return bottom;
            }

            public void setBottom(int bottom) {
                this.bottom = bottom;
            }
        }

        public static class LandmarkBean {
            /**
             * left_eyebrow_left_corner : {"x":393,"y":193}
             * left_eyebrow_middle : {"x":396,"y":222}
             * left_eyebrow_right_corner : {"x":386,"y":247}
             * right_eyebrow_left_corner : {"x":373,"y":290}
             * right_eyebrow_middle : {"x":371,"y":316}
             * right_eyebrow_right_corner : {"x":354,"y":338}
             * left_eye_left_corner : {"x":363,"y":200}
             * left_eye_right_corner : {"x":355,"y":235}
             * right_eye_left_corner : {"x":341,"y":285}
             * right_eye_right_corner : {"x":331,"y":319}
             * nose_left : {"x":307,"y":222}
             * nose_bottom : {"x":293,"y":247}
             * nose_right : {"x":294,"y":273}
             * mouth_upper_lip_top : {"x":271,"y":242}
             * mouth_middle : {"x":259,"y":238}
             * mouth_lower_lip_bottom : {"x":245,"y":235}
             * left_eye_center : {"x":362,"y":219}
             * right_eye_center : {"x":339,"y":303}
             * nose_top : {"x":314,"y":250}
             * mouth_left_corner : {"x":262,"y":197}
             * mouth_right_corner : {"x":244,"y":272}
             */

            private LeftEyebrowLeftCornerBean left_eyebrow_left_corner;
            private LeftEyebrowMiddleBean left_eyebrow_middle;
            private LeftEyebrowRightCornerBean left_eyebrow_right_corner;
            private RightEyebrowLeftCornerBean right_eyebrow_left_corner;
            private RightEyebrowMiddleBean right_eyebrow_middle;
            private RightEyebrowRightCornerBean right_eyebrow_right_corner;
            private LeftEyeLeftCornerBean left_eye_left_corner;
            private LeftEyeRightCornerBean left_eye_right_corner;
            private RightEyeLeftCornerBean right_eye_left_corner;
            private RightEyeRightCornerBean right_eye_right_corner;
            private NoseLeftBean nose_left;
            private NoseBottomBean nose_bottom;
            private NoseRightBean nose_right;
            private MouthUpperLipTopBean mouth_upper_lip_top;
            private MouthMiddleBean mouth_middle;
            private MouthLowerLipBottomBean mouth_lower_lip_bottom;
            private LeftEyeCenterBean left_eye_center;
            private RightEyeCenterBean right_eye_center;
            private NoseTopBean nose_top;
            private MouthLeftCornerBean mouth_left_corner;
            private MouthRightCornerBean mouth_right_corner;

            public LeftEyebrowLeftCornerBean getLeft_eyebrow_left_corner() {
                return left_eyebrow_left_corner;
            }

            public void setLeft_eyebrow_left_corner(LeftEyebrowLeftCornerBean left_eyebrow_left_corner) {
                this.left_eyebrow_left_corner = left_eyebrow_left_corner;
            }

            public LeftEyebrowMiddleBean getLeft_eyebrow_middle() {
                return left_eyebrow_middle;
            }

            public void setLeft_eyebrow_middle(LeftEyebrowMiddleBean left_eyebrow_middle) {
                this.left_eyebrow_middle = left_eyebrow_middle;
            }

            public LeftEyebrowRightCornerBean getLeft_eyebrow_right_corner() {
                return left_eyebrow_right_corner;
            }

            public void setLeft_eyebrow_right_corner(LeftEyebrowRightCornerBean left_eyebrow_right_corner) {
                this.left_eyebrow_right_corner = left_eyebrow_right_corner;
            }

            public RightEyebrowLeftCornerBean getRight_eyebrow_left_corner() {
                return right_eyebrow_left_corner;
            }

            public void setRight_eyebrow_left_corner(RightEyebrowLeftCornerBean right_eyebrow_left_corner) {
                this.right_eyebrow_left_corner = right_eyebrow_left_corner;
            }

            public RightEyebrowMiddleBean getRight_eyebrow_middle() {
                return right_eyebrow_middle;
            }

            public void setRight_eyebrow_middle(RightEyebrowMiddleBean right_eyebrow_middle) {
                this.right_eyebrow_middle = right_eyebrow_middle;
            }

            public RightEyebrowRightCornerBean getRight_eyebrow_right_corner() {
                return right_eyebrow_right_corner;
            }

            public void setRight_eyebrow_right_corner(RightEyebrowRightCornerBean right_eyebrow_right_corner) {
                this.right_eyebrow_right_corner = right_eyebrow_right_corner;
            }

            public LeftEyeLeftCornerBean getLeft_eye_left_corner() {
                return left_eye_left_corner;
            }

            public void setLeft_eye_left_corner(LeftEyeLeftCornerBean left_eye_left_corner) {
                this.left_eye_left_corner = left_eye_left_corner;
            }

            public LeftEyeRightCornerBean getLeft_eye_right_corner() {
                return left_eye_right_corner;
            }

            public void setLeft_eye_right_corner(LeftEyeRightCornerBean left_eye_right_corner) {
                this.left_eye_right_corner = left_eye_right_corner;
            }

            public RightEyeLeftCornerBean getRight_eye_left_corner() {
                return right_eye_left_corner;
            }

            public void setRight_eye_left_corner(RightEyeLeftCornerBean right_eye_left_corner) {
                this.right_eye_left_corner = right_eye_left_corner;
            }

            public RightEyeRightCornerBean getRight_eye_right_corner() {
                return right_eye_right_corner;
            }

            public void setRight_eye_right_corner(RightEyeRightCornerBean right_eye_right_corner) {
                this.right_eye_right_corner = right_eye_right_corner;
            }

            public NoseLeftBean getNose_left() {
                return nose_left;
            }

            public void setNose_left(NoseLeftBean nose_left) {
                this.nose_left = nose_left;
            }

            public NoseBottomBean getNose_bottom() {
                return nose_bottom;
            }

            public void setNose_bottom(NoseBottomBean nose_bottom) {
                this.nose_bottom = nose_bottom;
            }

            public NoseRightBean getNose_right() {
                return nose_right;
            }

            public void setNose_right(NoseRightBean nose_right) {
                this.nose_right = nose_right;
            }

            public MouthUpperLipTopBean getMouth_upper_lip_top() {
                return mouth_upper_lip_top;
            }

            public void setMouth_upper_lip_top(MouthUpperLipTopBean mouth_upper_lip_top) {
                this.mouth_upper_lip_top = mouth_upper_lip_top;
            }

            public MouthMiddleBean getMouth_middle() {
                return mouth_middle;
            }

            public void setMouth_middle(MouthMiddleBean mouth_middle) {
                this.mouth_middle = mouth_middle;
            }

            public MouthLowerLipBottomBean getMouth_lower_lip_bottom() {
                return mouth_lower_lip_bottom;
            }

            public void setMouth_lower_lip_bottom(MouthLowerLipBottomBean mouth_lower_lip_bottom) {
                this.mouth_lower_lip_bottom = mouth_lower_lip_bottom;
            }

            public LeftEyeCenterBean getLeft_eye_center() {
                return left_eye_center;
            }

            public void setLeft_eye_center(LeftEyeCenterBean left_eye_center) {
                this.left_eye_center = left_eye_center;
            }

            public RightEyeCenterBean getRight_eye_center() {
                return right_eye_center;
            }

            public void setRight_eye_center(RightEyeCenterBean right_eye_center) {
                this.right_eye_center = right_eye_center;
            }

            public NoseTopBean getNose_top() {
                return nose_top;
            }

            public void setNose_top(NoseTopBean nose_top) {
                this.nose_top = nose_top;
            }

            public MouthLeftCornerBean getMouth_left_corner() {
                return mouth_left_corner;
            }

            public void setMouth_left_corner(MouthLeftCornerBean mouth_left_corner) {
                this.mouth_left_corner = mouth_left_corner;
            }

            public MouthRightCornerBean getMouth_right_corner() {
                return mouth_right_corner;
            }

            public void setMouth_right_corner(MouthRightCornerBean mouth_right_corner) {
                this.mouth_right_corner = mouth_right_corner;
            }

            public static class LeftEyebrowLeftCornerBean {
                /**
                 * x : 393
                 * y : 193
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class LeftEyebrowMiddleBean {
                /**
                 * x : 396
                 * y : 222
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class LeftEyebrowRightCornerBean {
                /**
                 * x : 386
                 * y : 247
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class RightEyebrowLeftCornerBean {
                /**
                 * x : 373
                 * y : 290
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class RightEyebrowMiddleBean {
                /**
                 * x : 371
                 * y : 316
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class RightEyebrowRightCornerBean {
                /**
                 * x : 354
                 * y : 338
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class LeftEyeLeftCornerBean {
                /**
                 * x : 363
                 * y : 200
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class LeftEyeRightCornerBean {
                /**
                 * x : 355
                 * y : 235
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class RightEyeLeftCornerBean {
                /**
                 * x : 341
                 * y : 285
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class RightEyeRightCornerBean {
                /**
                 * x : 331
                 * y : 319
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class NoseLeftBean {
                /**
                 * x : 307
                 * y : 222
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class NoseBottomBean {
                /**
                 * x : 293
                 * y : 247
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class NoseRightBean {
                /**
                 * x : 294
                 * y : 273
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class MouthUpperLipTopBean {
                /**
                 * x : 271
                 * y : 242
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class MouthMiddleBean {
                /**
                 * x : 259
                 * y : 238
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class MouthLowerLipBottomBean {
                /**
                 * x : 245
                 * y : 235
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class LeftEyeCenterBean {
                /**
                 * x : 362
                 * y : 219
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class RightEyeCenterBean {
                /**
                 * x : 339
                 * y : 303
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class NoseTopBean {
                /**
                 * x : 314
                 * y : 250
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class MouthLeftCornerBean {
                /**
                 * x : 262
                 * y : 197
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class MouthRightCornerBean {
                /**
                 * x : 244
                 * y : 272
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }
        }
    }
}
