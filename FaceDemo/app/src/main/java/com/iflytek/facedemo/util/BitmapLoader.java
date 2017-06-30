package com.iflytek.facedemo.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianshang.liu on 2017/6/30.
 */

public class BitmapLoader {
    private static volatile BitmapLoader instance;
    private static Context mContext;
    private List<Bitmap> maps = new ArrayList<>();

    private BitmapLoader() {

    }

    public static synchronized BitmapLoader getInstance(Context aContext) {
        if (instance == null) {
            instance = new BitmapLoader();
            mContext = aContext.getApplicationContext();
        }
        return instance;
    }

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

    public List<Bitmap> getMaps() {
        if (maps.size()>1) {
            return maps;
        }
        String[] paths = null;
        try {
            paths = mContext.getAssets().list("huzi");
            for (int i = 0; i < paths.length; i++) {
                maps.add(loadAssets("huzi/"+paths[i]));
            }
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        return maps;
    }
}
