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

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static com.iflytek.facedemo.VideoDemo.count;
import static com.iflytek.facedemo.VideoDemo.headIndex;
import static com.iflytek.facedemo.VideoDemo.headMap;
import static com.iflytek.facedemo.VideoDemo.k;
import static com.iflytek.facedemo.VideoDemo.noseMap;

public class FaceUtil {
    public final static int REQUEST_PICTURE_CHOOSE = 1;
    public final static int REQUEST_CAMERA_IMAGE = 2;
    public final static int REQUEST_CROP_IMAGE = 3;

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

        List<Bitmap> noseMaps = BitmapLoader.getInstance(aContext).getNoseMaps();
        List<Bitmap> headMaps = BitmapLoader.getInstance(aContext).getHeadMaps();
        if (noseMaps.size() <= 22||headMaps.size()<=17) {
            return;
        }
        if (k == noseMaps.size()) {
            k = 0;
        }
        if(headIndex==headMaps.size()){
            headIndex=0;
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
//            int drawl = rect.left - len;
//            int drawr = rect.right + len;
//            int drawu = rect.top - len;
//            int drawd = rect.bottom + len;
//
//            canvas.drawLine(drawl, drawd, drawl, drawd - len, paint);
//            canvas.drawLine(drawl, drawd, drawl + len, drawd, paint);
//            canvas.drawLine(drawr, drawd, drawr, drawd - len, paint);
//            canvas.drawLine(drawr, drawd, drawr - len, drawd, paint);
//            canvas.drawLine(drawl, drawu, drawl, drawu + len, paint);
//            canvas.drawLine(drawl, drawu, drawl + len, drawu, paint);
//            canvas.drawLine(drawr, drawu, drawr, drawu + len, paint);
//            canvas.drawLine(drawr, drawu, drawr - len, drawu, paint);
        }


        if (face.point != null) {

            for (Point p : face.point) {
                if (frontCamera) {
                    p.y = width - p.y;
                }
//                canvas.drawPoint(p.x, p.y, paint);
            }
            noseMap = noseMaps.get(k);
            headMap = headMaps.get(headIndex);
            canvas.save();
            //----------------------------------鼻子---------------
            Point rnose = new Point(face.point[10].x, face.point[10].y);
            Point lnose = new Point(face.point[12].x, face.point[12].y);
            Point tnose = new Point(face.point[18].x, face.point[18].y);
            float tan = 1.0f * (rnose.y - lnose.y) / (rnose.x - lnose.x);
            double atan = Math.toDegrees(Math.atan(tan));
            float fa = noseMap.getHeight() * 1.0f / noseMap.getWidth();
            float newWidth = 5 * (rnose.x - lnose.x);
            float newHeight = newWidth * fa;
            Bitmap map = Bitmap.createScaledBitmap(noseMap, (int) newWidth, (int) newHeight, false);
            canvas.rotate((float) atan, tnose.x, tnose.y);
            canvas.drawBitmap(map, tnose.x - map.getWidth() / 2, tnose.y - map.getHeight() / 2, paint);
           canvas.restore();
            //------------增加两个额头点
            MyPoint rEye = new MyPoint(face.point[7].x, face.point[7].y);
            MyPoint lEye = new MyPoint(face.point[8].x, face.point[8].y);
            float noseLength = (float) Math.sqrt((rEye.x - rnose.x)*(rEye.x - rnose.x)+(rEye.y - rnose.y)*(rEye.y - rnose.y));
            MyPoint lHead = MathUtil.getThirdPointLeft(rEye,lEye,noseLength);
            MyPoint rHead = MathUtil.getThirdPointRight(rEye,lEye,noseLength);
            MyPoint mHead = new MyPoint((rHead.x+lHead.x)/2,(rHead.y+lHead.y)/2);
            float tan2 = 1.0f * (rEye.y - lEye.y) / (rEye.x - lEye.x);
            double atan2 = Math.toDegrees(Math.atan(tan2));
            float fa2 = headMap.getHeight() * 1.0f / headMap.getWidth();
            float newWidth2 = (float) (5.6 * noseLength);
            float newHeight2 = newWidth2 * fa2;
            Bitmap map2 = Bitmap.createScaledBitmap(headMap, (int) newWidth2, (int) newHeight2, false);
            canvas.rotate((float) atan2, mHead.x, mHead.y);
            canvas.drawBitmap(map2, mHead.x - map2.getWidth() / 2, mHead.y - map2.getHeight() / 2, paint);

            count++;
            k++;
            headIndex++;
            if (count % 2 == 0) {
            }
        }
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
