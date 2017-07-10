package com.iflytek.facedemo.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.iflytek.facedemo.util.BitmapLoader;
import com.iflytek.facedemo.util.JsonUtils;
import com.iflytek.facedemo.util.Lg;
import com.iflytek.facedemo.utils.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianshang.liu on 2017/7/10.
 * 单例对象，持有贴图对象，并负责配置相关信息
 */

public class MaskBeanProxy {

    private static volatile MaskBeanProxy instance;
    private final String FACE_DIR = "/face/";
    private final String FACE_JSON = "AfaceJson.json";

    private static Context appContext;
    //是否已经加载好
    private boolean isReady;
    //是否准备回收
    private boolean shouldRelease = false;

    public boolean isShouldRelease() {
        return shouldRelease;
    }

    public void setShouldRelease(boolean aShouldRelease) {
        shouldRelease = aShouldRelease;
    }

    public boolean isOnDrawing() {
        return onDrawing;
    }

    public void setOnDrawing(boolean aOnDrawing) {
        onDrawing = aOnDrawing;
    }

    private boolean onDrawing;
    //贴图对象
    private MaskBean mask;
    //当前贴图
    public Bitmap noseImg;
    public Bitmap headImg;
    public Bitmap eyeImg;
    public Bitmap chinImg;
    public Bitmap mouthImg;
    //贴图集合
    private List<Bitmap> noseImgs = new ArrayList<>();
    private List<Bitmap> headImgs = new ArrayList<>();
    private List<Bitmap> eyeImgs = new ArrayList<>();
    private List<Bitmap> chinImgs = new ArrayList<>();
    private List<Bitmap> mouthImgs = new ArrayList<>();

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean aReady) {
        isReady = aReady;
    }

    public MaskBean getMask() {
        return mask;
    }

    public void setMask(MaskBean aMask) {
        mask = aMask;
    }

    public Bitmap getNoseImg() {
        return noseImg;
    }

    public void setNoseImg(Bitmap aNoseImg) {
        noseImg = aNoseImg;
    }

    public Bitmap getHeadImg() {
        return headImg;
    }

    public void setHeadImg(Bitmap aHeadImg) {
        headImg = aHeadImg;
    }

    public Bitmap getEyeImg() {
        return eyeImg;
    }

    public void setEyeImg(Bitmap aEyeImg) {
        eyeImg = aEyeImg;
    }

    public Bitmap getChinImg() {
        return chinImg;
    }

    public void setChinImg(Bitmap aChinImg) {
        chinImg = aChinImg;
    }

    public Bitmap getMouthImg() {
        return mouthImg;
    }

    public List<Bitmap> getNoseImgs() {
        return noseImgs;
    }

    public List<Bitmap> getHeadImgs() {
        return headImgs;
    }

    public List<Bitmap> getEyeImgs() {
        return eyeImgs;
    }


    public List<Bitmap> getChinImgs() {
        return chinImgs;
    }

    public List<Bitmap> getMouthImgs() {
        return mouthImgs;
    }

    public void release() {
        instance.mask = null;
        instance = null;
        noseImgs.clear();
        headImgs.clear();
        eyeImgs.clear();
        chinImgs.clear();
        mouthImgs.clear();
        instance = single();
    }

    private String lastPath = "";

    public void update(String url, int location, int type) {
        if (lastPath.equals(url)) {
            return;
        }
        lastPath = url;
        Lg.trace("update(MaskBeanProxy.java:141)-->>" + url);
        if (!url.endsWith("zip")) {
            return;
        }
        //asset文件下
        if (location == 0) {
            try {
                InputStream ins = appContext.getAssets().open(url);
                try {
                    ZipUtils.unZipFiles(ZipUtils.writeBytesToFile(ins, ZipUtils.getFile(url)),
                            Environment.getExternalStorageDirectory() + FACE_DIR);
                    String mainDir = Environment.getExternalStorageDirectory() + FACE_DIR +
                            ZipUtils.getFileShortName(url);
                    String json = JsonUtils.getJsonFromFile(mainDir + File.separator + FACE_JSON);
                    Lg.trace("update(MaskBeanProxy.java:93)-->>" + json);
                    if (TextUtils.isEmpty(json)) {
                        return;
                    }
                    MaskBean bean = new Gson().fromJson(json, MaskBean.class);
                    if (bean == null) {
                        return;
                    }
                    MaskBeanProxy.single().setMask(bean);
                    BitmapLoader.loadImgsFromDir(this, mainDir);
                } catch (Exception aE) {
                    aE.printStackTrace();
                }
            } catch (IOException aE) {
                aE.printStackTrace();
            }
        }

    }

    private MaskBeanProxy() {

    }

    public static synchronized MaskBeanProxy single() {
        if (appContext == null) {
            try {
                throw new Exception("should invoke init method");
            } catch (Exception aE) {
                aE.printStackTrace();
            }
        }
        if (instance == null) {
            instance = new MaskBeanProxy();
        }
        return instance;
    }

    public static synchronized void init(Context aContext) {
        appContext = aContext.getApplicationContext();
    }

    public void autoChangeHead() {
        MaskBean.HeadBean head = mask.getHead();
        if (head == null) {
            return;
        }
        int index = head.getIndex();
        if (index >= head.getImgCount()) {
            index = 0;
        }
        headImg = headImgs.get(index);
        Lg.trace("auto-head" + index);
        index++;
        head.setIndex(index);
        mask.setHead(head);
        setMask(mask);
    }

    public void autoChangeEye() {
        MaskBean.EyeBean eye = mask.getEye();
        if (eye == null) {
            return;
        }
        int index = eye.getIndex();
        if (index >= eye.getImgCount()) {
            index = 0;
        }
        eyeImg = eyeImgs.get(index);
        Lg.trace("auto-eye" + index);
        index++;
        eye.setIndex(index);
        mask.setEye(eye);
        setMask(mask);
    }

    public void autoChangeNose() {
        MaskBean.NoseBean nose = mask.getNose();
        if (nose == null) {
            return;
        }
        int index = nose.getIndex();
        if (index >= nose.getImgCount()) {
            index = 0;
        }
        noseImg = noseImgs.get(index);
        Lg.trace("auto-nose" + index);
        index++;
        nose.setIndex(index);
        mask.setNose(nose);
        setMask(mask);
    }

    public void autoChangeMouth() {
        MaskBean.MouthBean mouth = mask.getMouth();
        if (mouth == null) {
            return;
        }
        int index = mouth.getIndex();
        if (index >= mouth.getImgCount()) {
            index = 0;
        }
        mouthImg = mouthImgs.get(index);
        Lg.trace("auto-mouth" + index);
        index++;
        mouth.setIndex(index);
        mask.setMouth(mouth);
        setMask(mask);
    }

    public void autoChangeChin() {
        MaskBean.ChinBean chin = mask.getChin();
        if (chin == null) {
            return;
        }
        int index = chin.getIndex();
        if (index >= chin.getImgCount()) {
            index = 0;
        }
        chinImg = chinImgs.get(index);
        Lg.trace("auto-chin" + index);
        index++;
        chin.setIndex(index);
        mask.setChin(chin);
        setMask(mask);
    }

    public void autoChangeIndex() {
        autoChangeChin();
        autoChangeEye();
        autoChangeHead();
        autoChangeMouth();
        autoChangeNose();
    }
}
