package com.iflytek.facedemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import static com.iflytek.facedemo.util.BitmapLoader.bearImg;
import static com.iflytek.facedemo.util.BitmapLoader.eyeImg;
import static com.iflytek.facedemo.util.BitmapLoader.eyeIndex;
import static com.iflytek.facedemo.util.BitmapLoader.headImg;
import static com.iflytek.facedemo.util.BitmapLoader.mouthImg;
import static com.iflytek.facedemo.util.BitmapLoader.mouthShow;
import static com.iflytek.facedemo.util.BitmapLoader.*;

public class FaceUtil {
    public final static int REQUEST_PICTURE_CHOOSE = 1;
    public final static int REQUEST_CAMERA_IMAGE = 2;
    public final static int REQUEST_CROP_IMAGE = 3;

    public static int count = 0;

    /***
     * 裁剪图片
     * @param activity Activity
     * @param uri 图片的Uri
     */
    public static void cropPicture(Activity activity, Uri uri) {
        Intent innerIntent = new Intent("com.android.camera.action.CROP");
        innerIntent.setDataAndType(uri, "image/*");
        innerIntent.putExtra("crop", "true");// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片
        innerIntent.putExtra("aspectX", 1); // 放大缩小比例的X
        innerIntent.putExtra("aspectY", 1);// 放大缩小比例的X   这里的比例为：   1:1
        innerIntent.putExtra("outputX", 320);  //这个是限制输出图片大小
        innerIntent.putExtra("outputY", 320);
        innerIntent.putExtra("return-data", true);
        // 切图大小不足输出，无黑框
        innerIntent.putExtra("scale", true);
        innerIntent.putExtra("scaleUpIfNeeded", true);
        File imageFile = new File(getImagePath(activity.getApplicationContext()));
        innerIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        innerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(innerIntent, REQUEST_CROP_IMAGE);
    }

    /**
     * 保存裁剪的图片的路径
     *
     * @return
     */
    public static String getImagePath(Context context) {
        String path;

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = context.getFilesDir().getAbsolutePath();
        } else {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/msc/";
        }

        if (!path.endsWith("/")) {
            path += "/";
        }

        File folder = new File(path);
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
        path += "ifd.jpg";
        return path;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree 旋转角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle  旋转角度
     * @param bitmap 原图
     * @return bitmap 旋转后的图片
     */
    public static Bitmap rotateImage(int angle, Bitmap bitmap) {
        // 图片旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 得到旋转后的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

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

    static public void drawFaceRect(Context aContext, Canvas canvas, FaceRect face, int width, int height, boolean frontCamera, boolean DrawOriRect) {
        if (canvas == null) {
            return;
        }
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
            BitmapLoader loader = BitmapLoader.getInstance(aContext);
            //若没准备好，则不绘制
            if (!loader.isImgsReady()) {
                return;
            }
            loader.initCurrentImg(BitmapLoader.FacePart.EYE);
            loader.initCurrentImg(BitmapLoader.FacePart.HEAD);
            loader.initCurrentImg(BitmapLoader.FacePart.NOSE);
            loader.initCurrentImg(BitmapLoader.FacePart.MOUTH);
            loader.initCurrentImg(BitmapLoader.FacePart.BOTTOM);
            //----------------鼻子---------------
            drawNose(canvas, face, paint);
            //------------画额头----------
            drawHead(canvas, face, paint);
            //画眼镜或者面具
            drawEyes(canvas, face, paint);
            //画下吧
            drawBottom(canvas, face, paint);
            //画上嘴唇
            drawTopMouth(canvas, face, paint);

            count++;
            if (count % 2 == 0) {
                noseIndex++;
                headIndex++;
                eyeIndex++;
                bearIndex++;
                mouthIndex++;
            }
            if (count > 1000000) {
                count = 0;
            }
        }
    }

    //绘制上嘴唇
    private static void drawTopMouth(Canvas canvas, FaceRect face, Paint aPaint) {
        if (!mouthShow) {
            return;
        }
        canvas.save();
        //配置
        float scaleOfImg = 0.6f;//图片缩放因子，相对左右嘴角的距离
        float offsetScaleOfWidth = 0.5f;//绘制起点相对图片的宽度偏移量比例
        float offsetScaleOfHeight = 1.0f;//绘制起点相对图片的高度偏移量比例
        //----------------------------------
        MyPoint topMouth = new MyPoint(face.point[13].x, face.point[13].y);
        MyPoint rMouth = new MyPoint(face.point[19].x, face.point[19].y);
        MyPoint lMouth = new MyPoint(face.point[20].x, face.point[20].y);
        float distance = MathUtil.getDistance(rMouth, lMouth);
        float degree = MathUtil.getDegree(rMouth, lMouth);
        float newWidth = distance * scaleOfImg;
        float newHeight = distance * scaleOfImg * mouthImg.getHeight() / mouthImg.getWidth();
        Bitmap targetMap = Bitmap.createScaledBitmap(mouthImg, (int) newWidth, (int) newHeight, false);
        canvas.rotate(degree, topMouth.x, topMouth.y);
        canvas.drawBitmap(targetMap, topMouth.x - targetMap.getWidth() * offsetScaleOfWidth, topMouth.y * offsetScaleOfHeight, aPaint);
        canvas.restore();
    }

    //绘制下巴
    private static void drawBottom(Canvas canvas, FaceRect face, Paint aPaint) {
        if (!BitmapLoader.bearShow) {
            return;
        }
        canvas.save();
        //配置
        float scaleOfImg = 5f;//图片缩放因子，相对眼和鼻子的距离来说
        float offsetScaleOfWidth = 0.5f;//绘制起点相对图片的宽度偏移量
        float offsetScaleOfHeight = 0.5f;//绘制起点相对图片的高度偏移量
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
        float newHeight = noseLength * scaleOfImg * bearImg.getHeight() / bearImg.getWidth();
        Bitmap targetMap = Bitmap.createScaledBitmap(bearImg, (int) newWidth, (int) newHeight, false);
        canvas.rotate(degree, mBottom.x, mBottom.y);
        canvas.drawBitmap(targetMap, mBottom.x - targetMap.getWidth() * offsetScaleOfWidth,
                mBottom.y - targetMap.getHeight() * offsetScaleOfHeight, aPaint);
        canvas.restore();
    }

    //绘制眼睛
    private static void drawEyes(Canvas canvas, FaceRect face, Paint aPaint) {
        if (!BitmapLoader.eyeShow) {
            return;
        }
        canvas.save();
        //配置
        float scaleOfImg = 5f;//图片缩放因子，相对眼睛的宽度
        float offsetScaleOfWidth = 0.5f;//绘制起点相对图片的宽度偏移量
        float offsetScaleOfHeight = 0.5f;//绘制起点相对图片的高度偏移量
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
        float newHeight = scaleOfImg * eyeInterval * eyeImg.getHeight() / eyeImg.getWidth();
        Bitmap targetMap = Bitmap.createScaledBitmap(eyeImg, (int) newWidth, (int) newHeight, false);
        double sAtanEyes = MathUtil.getDegree(sREye, sLEye);
        canvas.rotate((float) sAtanEyes, midEye.x, midEye.y);
        canvas.drawBitmap(targetMap, midEye.x - targetMap.getWidth() * offsetScaleOfWidth,
                midEye.y - targetMap.getHeight() * offsetScaleOfHeight, aPaint);
        canvas.restore();
    }

    //绘制额头
    private static void drawHead(Canvas canvas, FaceRect face, Paint aPaint) {
        if (!BitmapLoader.headShow) {
            return;
        }
        canvas.save();
        //配置
        float scaleOfImg = 5f;//图片缩放因子，相对鼻子左右点的距离
        float scaleOfOffSet = 1.5f;//偏移量因子，基础值是鼻子和眼镜的距离
        float offsetScaleOfWidth = 0.5f;//绘制起点相对图片的宽度偏移量
        float offsetScaleOfHeight = 0.5f;//绘制起点相对图片的高度偏移量
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
        float newHeight = newWidth * headImg.getHeight() / headImg.getWidth();
        Bitmap targetMap = Bitmap.createScaledBitmap(headImg, (int) newWidth, (int) newHeight, false);
        canvas.rotate(sAtanEyes, mHead.x, mHead.y);
        canvas.drawBitmap(targetMap, mHead.x - targetMap.getWidth() * offsetScaleOfWidth,
                mHead.y - targetMap.getHeight() * offsetScaleOfHeight, aPaint);
        canvas.restore();
    }

    // 绘制鼻子
    private static void drawNose(Canvas canvas, FaceRect face, Paint aPaint) {
        if (!BitmapLoader.noseShow) {
            return;
        }
        canvas.save();
        //配置
        float scaleOfImg = 4.5f;//图片缩放因子，相对鼻子左右点的距离
        float offsetScaleOfWidth = 0.5f;//绘制起点相对图片的宽度偏移量
        float offsetScaleOfHeight = 0.5f;//绘制起点相对图片的高度偏移量
        //----------------------------------
        MyPoint rnose = new MyPoint(face.point[10].x, face.point[10].y);
        MyPoint lnose = new MyPoint(face.point[12].x, face.point[12].y);
        MyPoint tnose = new MyPoint(face.point[18].x, face.point[18].y);
        float atan = MathUtil.getDegree(rnose, lnose);
        float distance = MathUtil.getDistance(rnose, lnose);
        float newWidth = scaleOfImg * distance;
        float newHeight = newWidth * noseImg.getHeight() / noseImg.getWidth();
        Bitmap targetMap = Bitmap.createScaledBitmap(noseImg, (int) newWidth, (int) newHeight, false);
        canvas.rotate(atan, tnose.x, tnose.y);
        canvas.drawBitmap(targetMap, tnose.x - targetMap.getWidth() * offsetScaleOfWidth,
                tnose.y - targetMap.getHeight() * offsetScaleOfHeight, aPaint);
        canvas.restore();
    }

    /**
     * 将矩形随原图顺时针旋转90度
     *
     * @param r      待旋转的矩形
     * @param width  输入矩形对应的原图宽
     * @param height 输入矩形对应的原图高
     * @return 旋转后的矩形
     */
    static public Rect RotateDeg90(Rect r, int width, int height) {
        int left = r.left;
        r.left = height - r.bottom;
        r.bottom = r.right;
        r.right = height - r.top;
        r.top = left;
        return r;
    }

    /**
     * 将点随原图顺时针旋转90度
     *
     * @param p      待旋转的点
     * @param width  输入点对应的原图宽
     * @param height 输入点对应的原图宽
     * @return 旋转后的点
     */
    static public Point RotateDeg90(Point p, int width, int height) {
        int x = p.x;
        p.x = height - p.y;
        p.y = x;
        return p;
    }

    public static int getNumCores() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 保存Bitmap至本地
     */
    public static void saveBitmapToFile(Context context, Bitmap bmp) {
        String file_path = getImagePath(context);
        File file = new File(file_path);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
