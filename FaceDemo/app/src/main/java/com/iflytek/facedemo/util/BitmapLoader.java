package com.iflytek.facedemo.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianshang.liu on 2017/6/30.
 */

public class BitmapLoader {
    private static volatile BitmapLoader instance;
    public static Bitmap noseImg;
    public static Bitmap headImg;
    public static Bitmap eyeImg;
    public static Bitmap bearImg;
    public static Bitmap mouthImg;
    //----开关
    public static boolean noseShow = false;
    public static boolean headShow = false;
    public static boolean eyeShow = false;
    public static boolean bearShow = false;
    public static boolean mouthShow = false;
    //
    private static Context mContext;
    private List<Bitmap> noseImgs = new ArrayList<>();
    private List<Bitmap> headImgs = new ArrayList<>();
    private List<Bitmap> eyeImgs = new ArrayList<>();
    private List<Bitmap> bearImgs = new ArrayList<>();
    private List<Bitmap> mouthImgs = new ArrayList<>();

    private BitmapLoader() {

    }

    public static synchronized BitmapLoader getInstance(Context aContext) {
        if (instance == null) {
            instance = new BitmapLoader();
            mContext = aContext.getApplicationContext();
        }
        return instance;
    }

    //从asset里读取文件
    public Bitmap loadAssets(String fileName) {
        Bitmap image = null;
        AssetManager am = mContext.getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    //获得nose贴图包
    public List<Bitmap> getNoseImgs() {
        if (noseImgs.size() > 1) {
            return noseImgs;
        }
        String[] paths = null;
        try {
            paths = mContext.getAssets().list("huzi");
            for (int i = 0; i < paths.length; i++) {
                noseImgs.add(loadAssets("huzi/" + paths[i]));
            }
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        return noseImgs;
    }

    //获得头部贴图包
    public List<Bitmap> getHeadImgs() {
        if (headImgs.size() > 1) {
            return headImgs;
        }
        String[] paths = null;
        try {
            paths = mContext.getAssets().list("ear");
            for (int i = 0; i < paths.length; i++) {
                headImgs.add(loadAssets("ear/" + paths[i]));
            }
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        return headImgs;
    }

    //获得眼镜贴图包
    public List<Bitmap> getEyeImgs() {
        if (eyeImgs.size() > 1) {
            return eyeImgs;
        }
        String[] paths = null;
        try {
            paths = mContext.getAssets().list("eye");
            for (int i = 0; i < paths.length; i++) {
                eyeImgs.add(loadAssets("eye/" + paths[i]));
            }
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        return eyeImgs;
    }

    //获得下吧贴图包
    public List<Bitmap> getBearImgs() {
        if (bearImgs.size() > 1) {
            return bearImgs;
        }
        String[] paths = null;
        try {
            paths = mContext.getAssets().list("bear");
            for (int i = 0; i < paths.length; i++) {
                bearImgs.add(loadAssets("bear/" + paths[i]));
            }
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        return bearImgs;
    }

    //获得上嘴巴贴图包
    public List<Bitmap> getMouthImgs() {
        if (mouthImgs.size() > 1) {
            return mouthImgs;
        }
        String[] paths = null;
        try {
            paths = mContext.getAssets().list("mouth");
            for (int i = 0; i < paths.length; i++) {
                mouthImgs.add(loadAssets("mouth/" + paths[i]));
            }
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        return mouthImgs;
    }
}
