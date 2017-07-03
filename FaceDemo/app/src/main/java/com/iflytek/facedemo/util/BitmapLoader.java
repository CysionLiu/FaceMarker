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
    private static Context mContext;
    private List<Bitmap> noseImgs = new ArrayList<>();
    private List<Bitmap> headImgs = new ArrayList<>();

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
}
