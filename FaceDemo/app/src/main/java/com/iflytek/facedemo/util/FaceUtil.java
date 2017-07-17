package com.iflytek.facedemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.iflytek.facedemo.entity.MaskBean;
import com.iflytek.facedemo.entity.MaskBeanProxy;

public class FaceUtil {

    public static int count = 0;
    private static MaskBeanProxy sMaskBeanProxy;
    private static MaskBean sMaskBean;

    /**
     * 在指定画布上将人脸框出来
     *
     * @param canvas      给定的画布
     * @param face        需要绘制的人脸信息
     * @param width       原图宽
     * @param height      原图高
     * @param frontCamera 是否为前置摄像头，如为前置摄像头需左右对称
     * @param DrawOriRect 可绘制原始框，也可以只画四个角
     */

    static public synchronized void drawFaceRect(Context aContext, Canvas canvas, FaceRect face, int width, int height, boolean frontCamera, boolean DrawOriRect) {
        if (canvas == null) {
            return;
        }
        Log.e("flag--","drawFaceRect(FaceUtil.java:36)-->>"+System.currentTimeMillis());
        Paint paint = new Paint();
        Rect rect = face.bound;
        if (frontCamera) {
            int top = rect.top;
            rect.top = width - rect.bottom;
            rect.bottom = width - top;
        }

        if (DrawOriRect) {
            paint.setStyle(Style.STROKE);
            canvas.drawRect(rect, paint);
        }
        if (face.point != null) {
            //校正点的坐标
            for (Point p : face.point) {
                if (frontCamera) {
                    p.y = width - p.y;
                }
            }
            sMaskBeanProxy = MaskBeanProxy.single();
            if (sMaskBeanProxy.isShouldRelease()) {
                return;
            }
            Lg.trace("draw-not release");
            sMaskBean = sMaskBeanProxy.getMask();
            if (sMaskBeanProxy == null || sMaskBean == null) {
                return;
            }
            Lg.trace("draw-not null");
            if (!sMaskBeanProxy.isReady()) {
                return;
            }
            Lg.trace("draw-ready");
            sMaskBeanProxy.setOnDrawing(true);
            //----------------鼻子---------------
            drawNose(canvas, face, paint);
            //------------画额头----------
            drawHead(canvas, face, paint);
            //画眼镜或者面具
            drawEyes(canvas, face, paint);
            //画下吧
            drawChin(canvas, face, paint);
            //画上嘴唇
            drawTopMouth(canvas, face, paint);

            count++;
            if (count % 3 == 0) {
                sMaskBeanProxy.autoChangeIndex();
            }
            if (count > 1000000) {
                count = 0;
            }
            sMaskBeanProxy.setOnDrawing(false);
        }
    }

    //绘制上嘴唇
    private static void drawTopMouth(Canvas canvas, FaceRect face, Paint aPaint) {
        MaskBean.MouthBean mouth = sMaskBeanProxy.getMask().getMouth();
        if (mouth == null) {
            return;
        }
        if (!mouth.isWillShow()) {
            return;
        }
        canvas.save();
        //配置
        float scaleOfImg = (float) mouth.getScale();//图片缩放因子，相对左右嘴角的距离
        float offsetScaleOfWidth = (float) mouth.getWOffset();//绘制起点相对图片的宽度偏移量比例
        float offsetScaleOfHeight = (float) mouth.getHOffset();//绘制起点相对图片的高度偏移量比例
        //----------------------------------
        MyPoint topMouth = new MyPoint(face.point[13].x, face.point[13].y);
        MyPoint rMouth = new MyPoint(face.point[19].x, face.point[19].y);
        MyPoint lMouth = new MyPoint(face.point[20].x, face.point[20].y);
        float distance = MathUtil.getDistance(rMouth, lMouth);
        float degree = MathUtil.getDegree(rMouth, lMouth);
        float newWidth = distance * scaleOfImg;
        float newHeight = distance * scaleOfImg * sMaskBeanProxy.getMouthImg().getHeight() / sMaskBeanProxy.getMouthImg().getWidth();
        Bitmap targetMap = Bitmap.createScaledBitmap(sMaskBeanProxy.getMouthImg(), (int) newWidth, (int) newHeight, false);
        canvas.rotate(degree, topMouth.x, topMouth.y);
        canvas.drawBitmap(targetMap, topMouth.x - targetMap.getWidth() * offsetScaleOfWidth, topMouth.y * offsetScaleOfHeight, aPaint);
        canvas.restore();
    }

    //绘制下巴
    private static void drawChin(Canvas canvas, FaceRect face, Paint aPaint) {
        MaskBean.ChinBean chin = sMaskBeanProxy.getMask().getChin();
        if (chin == null) {
            return;
        }
        if (!chin.isWillShow()) {
            return;
        }
        canvas.save();
        //配置
        float scaleOfImg = (float) chin.getScale();//图片缩放因子，相对眼和鼻子的距离来说
        float offsetScaleOfWidth = (float) chin.getWOffset();//绘制起点相对图片的宽度偏移量
        float offsetScaleOfHeight = (float) chin.getHOffset();//绘制起点相对图片的高度偏移量
        //----------------------------------
        MyPoint sREye = new MyPoint(face.point[7].x, face.point[7].y);
        MyPoint aRnose = new MyPoint(face.point[10].x, face.point[10].y);
        MyPoint rMouth = new MyPoint(face.point[19].x, face.point[19].y);
        MyPoint lMouth = new MyPoint(face.point[20].x, face.point[20].y);
        //鼻子和眼镜的距离
        float noseLength = MathUtil.getDistance(sREye, aRnose);
        MyPoint mBottom = new MyPoint(face.point[15].x, face.point[15].y);
        //嘴唇旋转角度
        float degree = MathUtil.getDegree(rMouth, lMouth);
        //图片新宽高
        float newWidth = scaleOfImg * noseLength;
        float newHeight = noseLength * scaleOfImg * sMaskBeanProxy.getChinImg().getHeight() /
                sMaskBeanProxy.getChinImg().getWidth();
        Bitmap targetMap = Bitmap.createScaledBitmap(sMaskBeanProxy.getChinImg(), (int) newWidth, (int) newHeight, false);
        canvas.rotate(degree, mBottom.x, mBottom.y);
        canvas.drawBitmap(targetMap, mBottom.x - targetMap.getWidth() * offsetScaleOfWidth,
                mBottom.y - targetMap.getHeight() * offsetScaleOfHeight, aPaint);
        canvas.restore();
    }

    //绘制眼睛
    private static void drawEyes(Canvas canvas, FaceRect face, Paint aPaint) {
        MaskBean.EyeBean eye = sMaskBean.getEye();
        if (eye == null) {
            return;
        }
        if (!eye.isWillShow()) {
            return;
        }
        canvas.save();
        //配置
        float scaleOfImg = (float) eye.getScale();//图片缩放因子，相对眼睛的宽度
        float offsetScaleOfWidth = (float) eye.getWOffset();//绘制起点相对图片的宽度偏移量
        float offsetScaleOfHeight = (float) eye.getHOffset();//绘制起点相对图片的高度偏移量
        //----------------------------------
        MyPoint sREye = new MyPoint(face.point[7].x, face.point[7].y);
        MyPoint sLEye = new MyPoint(face.point[8].x, face.point[8].y);
        MyPoint midEye = MathUtil.getMiddle(sLEye, sREye);
        float rightEyeLength = MathUtil.getDistance(sREye, new MyPoint(face.point[6].x, face.point[6].y));
        float leftEyeLength = MathUtil.getDistance(sLEye, new MyPoint(face.point[9].x, face.point[9].y));
        //更长的眼睛作为距离
        float eyeInterval = rightEyeLength > leftEyeLength ? rightEyeLength : leftEyeLength;
        //图片新宽高
        float newWidth = scaleOfImg * eyeInterval;
        float newHeight = scaleOfImg * eyeInterval * sMaskBeanProxy.getEyeImg().getHeight() /
                sMaskBeanProxy.getEyeImg().getWidth();
        Bitmap targetMap = Bitmap.createScaledBitmap(sMaskBeanProxy.getEyeImg(), (int) newWidth, (int) newHeight, false);
        double sAtanEyes = MathUtil.getDegree(sREye, sLEye);
        canvas.rotate((float) sAtanEyes, midEye.x, midEye.y);
        canvas.drawBitmap(targetMap, midEye.x - targetMap.getWidth() * offsetScaleOfWidth,
                midEye.y - targetMap.getHeight() * offsetScaleOfHeight, aPaint);
        canvas.restore();
    }

    //绘制额头
    private static void drawHead(Canvas canvas, FaceRect face, Paint aPaint) {
        MaskBean.HeadBean head = sMaskBean.getHead();
        if (head == null) {
            return;
        }
        if (!head.isWillShow()) {
            return;
        }
        canvas.save();
        //配置
        float scaleOfImg = (float) head.getScale();//图片缩放因子，相对鼻子左右点的距离
        float scaleOfOffSet = (float) head.getOffset();//偏移量因子，基础值是鼻子和眼镜的距离
        float offsetScaleOfWidth = (float) head.getWOffset();//绘制起点相对图片的宽度偏移量
        float offsetScaleOfHeight = (float) head.getHOffset();//绘制起点相对图片的高度偏移量
        //----------------------------------
        MyPoint aRnose = new MyPoint(face.point[10].x, face.point[10].y);
        MyPoint sREye = new MyPoint(face.point[7].x, face.point[7].y);
        MyPoint sLEye = new MyPoint(face.point[8].x, face.point[8].y);
        //获得眼睛和鼻子的距离
        float noseLength = MathUtil.getDistance(sREye, aRnose);
        //计算得到第3,4,个点，基础偏移量是眼睛和鼻子的距离
        MyPoint lHead = MathUtil.getThirdPointLeft(sREye, sLEye, noseLength * scaleOfOffSet);
        MyPoint rHead = MathUtil.getThirdPointRight(sREye, sLEye, noseLength * scaleOfOffSet);
        MyPoint mHead = new MyPoint((rHead.x + lHead.x) / 2, (rHead.y + lHead.y) / 2);
        //获得旋转角度
        float sAtanEyes = MathUtil.getDegree(sREye, sLEye);
        //新图片宽高
        float newWidth = scaleOfImg * noseLength;
        float newHeight = newWidth * sMaskBeanProxy.getHeadImg().getHeight() / sMaskBeanProxy.getHeadImg().getWidth();
        Bitmap targetMap = Bitmap.createScaledBitmap(sMaskBeanProxy.getHeadImg(), (int) newWidth, (int) newHeight, false);
        canvas.rotate(sAtanEyes, mHead.x, mHead.y);
        canvas.drawBitmap(targetMap, mHead.x - targetMap.getWidth() * offsetScaleOfWidth,
                mHead.y - targetMap.getHeight() * offsetScaleOfHeight, aPaint);
        canvas.restore();
    }

    // 绘制鼻子
    private static void drawNose(Canvas canvas, FaceRect face, Paint aPaint) {
        MaskBean.NoseBean nose = sMaskBean.getNose();
        if (nose == null) {
            return;
        }
        if (!nose.isWillShow()) {
            return;
        }
        canvas.save();
        //配置
        float scaleOfImg = (float) nose.getScale();//图片缩放因子，相对鼻子左右点的距离
        float offsetScaleOfWidth = (float) nose.getWOffset();//绘制起点相对图片的宽度偏移量
        float offsetScaleOfHeight = (float) nose.getHOffset();//绘制起点相对图片的高度偏移量
        //----------------------------------
        MyPoint rnose = new MyPoint(face.point[10].x, face.point[10].y);
        MyPoint lnose = new MyPoint(face.point[12].x, face.point[12].y);
        MyPoint tnose = new MyPoint(face.point[18].x, face.point[18].y);
        float atan = MathUtil.getDegree(rnose, lnose);
        float distance = MathUtil.getDistance(rnose, lnose);
        float newWidth = scaleOfImg * distance;
        float newHeight = newWidth * sMaskBeanProxy.getNoseImg().getHeight() / sMaskBeanProxy.getNoseImg().getWidth();
        Bitmap targetMap = Bitmap.createScaledBitmap(sMaskBeanProxy.getNoseImg(), (int) newWidth, (int) newHeight, false);
        canvas.rotate(atan, tnose.x, tnose.y);
        canvas.drawBitmap(targetMap, tnose.x - targetMap.getWidth() * offsetScaleOfWidth,
                tnose.y - targetMap.getHeight() * offsetScaleOfHeight, aPaint);
        canvas.restore();
    }
}
