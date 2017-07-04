package com.iflytek.facedemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static com.iflytek.facedemo.util.BitmapLoader.bearImg;
import static com.iflytek.facedemo.util.BitmapLoader.eyeImg;
import static com.iflytek.facedemo.util.BitmapLoader.headImg;
import static com.iflytek.facedemo.util.BitmapLoader.mouthImg;
import static com.iflytek.facedemo.util.BitmapLoader.mouthShow;
import static com.iflytek.facedemo.util.BitmapLoader.noseImg;

public class FaceUtil {
    public final static int REQUEST_PICTURE_CHOOSE = 1;
    public final static int REQUEST_CAMERA_IMAGE = 2;
    public final static int REQUEST_CROP_IMAGE = 3;
    public static int noseIndex = 0;
    public static int headIndex = 0;
    public static int eyeIndex = 0;
    public static int bearIndex = 0;
    public static int mouthIndex = 0;
    public static int count = 0;
    private static MyPoint sREye;
    private static MyPoint sLEye;
    private static double sAtanEyes;

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
        List<Bitmap> noseImgs = BitmapLoader.getInstance(aContext).getNoseImgs();
        List<Bitmap> headImgs = BitmapLoader.getInstance(aContext).getHeadImgs();
        List<Bitmap> eyeImgs = BitmapLoader.getInstance(aContext).getEyeImgs();
        List<Bitmap> bearImgs = BitmapLoader.getInstance(aContext).getBearImgs();
        List<Bitmap> mouthImgs = BitmapLoader.getInstance(aContext).getMouthImgs();
        if (noseImgs.size() <= 22 || headImgs.size() <= 17 || eyeImgs.size() < 1 || bearImgs.size() < 1 || mouthImgs.size() < 1) {
            return;
        }
        if (noseIndex == noseImgs.size()) {
            noseIndex = 0;
        }
        if (headIndex == headImgs.size()) {
            headIndex = 0;
        }
        if (eyeIndex == eyeImgs.size()) {
            eyeIndex = 0;
        }
        if (bearIndex == bearImgs.size()) {
            bearIndex = 0;
        }
        if (mouthIndex == mouthImgs.size()) {
            mouthIndex = 0;
        }
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 203, 15));
        int len = (face.bound.bottom - face.bound.top) / 8;
        if (len / 8 >= 2) paint.setStrokeWidth(len / 8);
        else paint.setStrokeWidth(2);

        Rect rect = face.bound;

        if (frontCamera) {
            int top = rect.top;
            rect.top = width - rect.bottom;
            rect.bottom = width - top;
        }

        if (DrawOriRect) {
            paint.setStyle(Style.STROKE);
            canvas.drawRect(rect, paint);
        } else {
        }

        if (face.point != null) {

            for (Point p : face.point) {
                if (frontCamera) {
                    p.y = width - p.y;
                }
//                canvas.drawPoint(p.x, p.y, paint);
            }
            noseImg = noseImgs.get(noseIndex);
            headImg = headImgs.get(headIndex);
            eyeImg = eyeImgs.get(eyeIndex);
            bearImg = bearImgs.get(bearIndex);
            mouthImg = mouthImgs.get(mouthIndex);
            //----------------鼻子---------------
            MyPoint rnose = drawNose(canvas, face, paint);
            //------------增加两个额头点----------
            drawHead(canvas, face, paint, rnose);
            //画眼镜或者面具
            drawEyes(canvas, face, paint);

            //画下吧
            drawBottom(canvas, face, paint, rnose);

            //画上嘴唇
            drawTopMouth(canvas, face, paint);


            count++;
            if (count % 2 == 0) {
                noseIndex++;
                headIndex++;
                eyeIndex++;
            }
        }
    }

    private static void drawTopMouth(Canvas canvas, FaceRect face, Paint aPaint) {
        if (!mouthShow) {
            return;
        }
        canvas.save();
        MyPoint topMouth = new MyPoint(face.point[13].x, face.point[13].y);
        MyPoint rMouth = new MyPoint(face.point[19].x, face.point[19].y);
        MyPoint lMouth = new MyPoint(face.point[20].x, face.point[20].y);
        float distance = MathUtil.getDistance(rMouth, lMouth);
        float degree = MathUtil.getDegree(rMouth, lMouth);
        float scale = 0.6f;
        float newWidth2 = distance * scale;
        float newHeight2 = distance * scale * mouthImg.getHeight() / mouthImg.getWidth();
        Bitmap map2 = Bitmap.createScaledBitmap(mouthImg, (int) newWidth2, (int) newHeight2, false);
        canvas.rotate((float) degree, topMouth.x, topMouth.y);
        canvas.drawBitmap(map2, topMouth.x - map2.getWidth() / 2, topMouth.y, aPaint);
        canvas.restore();
    }

    private static void drawBottom(Canvas canvas, FaceRect face, Paint aPaint, MyPoint aRnose) {
        if (!BitmapLoader.bearShow) {
            return;
        }
        canvas.save();
        MyPoint rMouth = new MyPoint(face.point[19].x, face.point[19].y);
        MyPoint lMouth = new MyPoint(face.point[20].x, face.point[20].y);
        float noseLength = MathUtil.getDistance(sREye, aRnose);
        MyPoint lBottom = MathUtil.getThirdPointRightDown(rMouth, lMouth, noseLength / 3);
        MyPoint rBottom = MathUtil.getThirdPointLeftUp(rMouth, lMouth, noseLength / 3);
        MyPoint mBottom = MathUtil.getMiddle(rBottom, lBottom);
        float degree = MathUtil.getDegree(rBottom, lBottom);
        float fa2 = bearImg.getHeight() * 1.0f / bearImg.getWidth();
        float newWidth2 = (float) (6 * noseLength);
        float newHeight2 = newWidth2 * fa2;
        Bitmap map2 = Bitmap.createScaledBitmap(bearImg, (int) newWidth2, (int) newHeight2, false);
        canvas.rotate((float) degree, mBottom.x, mBottom.y);
        canvas.drawBitmap(map2, mBottom.x - map2.getWidth() / 2, mBottom.y - map2.getHeight() / 2, aPaint);
        canvas.restore();
    }

    private static void drawEyes(Canvas canvas, FaceRect face, Paint aPaint) {
        if (!BitmapLoader.eyeShow) {
            return;
        }
        canvas.save();
        MyPoint midEye = MathUtil.getMiddle(sLEye, sREye);
        float rightEyeLength = MathUtil.getDistance(sREye, new MyPoint(face.point[6].x, face.point[6].y));
        float leftEyeLength = MathUtil.getDistance(sLEye, new MyPoint(face.point[9].x, face.point[9].y));
        float eyeInterval = rightEyeLength > leftEyeLength ? rightEyeLength : leftEyeLength;//更长的眼睛
        float fa2 = eyeImg.getHeight() * 1.0f / eyeImg.getWidth();
        float newWidth3 = (float) (6 * eyeInterval);
        float newHeight3 = newWidth3 * fa2;
        Bitmap map2 = Bitmap.createScaledBitmap(eyeImg, (int) newWidth3, (int) newHeight3, false);
        sAtanEyes = MathUtil.getDegree(sREye, sLEye);
        canvas.rotate((float) sAtanEyes, midEye.x, midEye.y);
        canvas.drawBitmap(map2, midEye.x - map2.getWidth() / 2, midEye.y - map2.getHeight() / 2, aPaint);
        canvas.restore();
    }

    private static void drawHead(Canvas canvas, FaceRect face, Paint aPaint, MyPoint aRnose) {
        sREye = new MyPoint(face.point[7].x, face.point[7].y);
        sLEye = new MyPoint(face.point[8].x, face.point[8].y);
        if (!BitmapLoader.headShow) {
            return;
        }
        canvas.save();
        float noseLength = (float) Math.sqrt((sREye.x - aRnose.x) * (sREye.x - aRnose.x) + (sREye.y - aRnose.y) * (sREye.y - aRnose.y));
        MyPoint lHead = MathUtil.getThirdPointLeft(sREye, sLEye, noseLength);
        MyPoint rHead = MathUtil.getThirdPointRight(sREye, sLEye, noseLength);
        MyPoint mHead = new MyPoint((rHead.x + lHead.x) / 2, (rHead.y + lHead.y) / 2);
        float tanEyes = 1.0f * (sREye.y - sLEye.y) / (sREye.x - sLEye.x);
        sAtanEyes = Math.toDegrees(Math.atan(tanEyes));
        float fa2 = headImg.getHeight() * 1.0f / headImg.getWidth();
        float newWidth2 = (float) (5.6 * noseLength);
        float newHeight2 = newWidth2 * fa2;
        Bitmap map2 = Bitmap.createScaledBitmap(headImg, (int) newWidth2, (int) newHeight2, false);
        canvas.rotate((float) sAtanEyes, mHead.x, mHead.y);
        canvas.drawBitmap(map2, mHead.x - map2.getWidth() / 2, mHead.y - map2.getHeight() / 2, aPaint);
        canvas.restore();
    }

    @NonNull
    private static MyPoint drawNose(Canvas canvas, FaceRect face, Paint aPaint) {
        canvas.save();
        MyPoint rnose = new MyPoint(face.point[10].x, face.point[10].y);
        MyPoint lnose = new MyPoint(face.point[12].x, face.point[12].y);
        MyPoint tnose = new MyPoint(face.point[18].x, face.point[18].y);
        if (!BitmapLoader.noseShow) {
            canvas.restore();
            return rnose;
        }
        double atan = MathUtil.getDegree(rnose, lnose);
        float fa = noseImg.getHeight() * 1.0f / noseImg.getWidth();
        float newWidth = 5 * (rnose.x - lnose.x);
        float newHeight = newWidth * fa;
        Bitmap map = Bitmap.createScaledBitmap(noseImg, (int) newWidth, (int) newHeight, false);
        canvas.rotate((float) atan, tnose.x, tnose.y);
        canvas.drawBitmap(map, tnose.x - map.getWidth() / 2, tnose.y - map.getHeight() / 2, aPaint);
        canvas.restore();
        return rnose;
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
