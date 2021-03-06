package com.iflytek.facedemo.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.iflytek.facedemo.entity.MaskBean;
import com.iflytek.facedemo.entity.MaskBeanProxy;

import java.io.File;
import java.io.FileInputStream;
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
    //图片数量
    public int noseLength = 12;
    public int headLength = 17;
    public int eyeLength = 3;
    public int bearLength = 1;
    public int mouthLength = 1;
    //
    public static int noseIndex = 0;
    public static int headIndex = 0;
    public static int eyeIndex = 0;
    public static int bearIndex = 0;
    public static int mouthIndex = 0;
    private static Context mContext;
    private List<Bitmap> noseImgs = new ArrayList<>();
    private List<Bitmap> headImgs = new ArrayList<>();
    private List<Bitmap> eyeImgs = new ArrayList<>();
    private List<Bitmap> bearImgs = new ArrayList<>();
    private List<Bitmap> mouthImgs = new ArrayList<>();

    private BitmapLoader() {
        //加载图片
        loadImgs();
    }

    private void loadImgs() {
        getBearImgs();
        getEyeImgs();
        getHeadImgs();
        getMouthImgs();
        getNoseImgs();
    }


    public static synchronized BitmapLoader getInstance(Context aContext) {
        if (instance == null) {
            mContext = aContext.getApplicationContext();
            instance = new BitmapLoader();

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
            paths = mContext.getAssets().list("huxu");
            for (int i = 0; i < paths.length; i++) {
                noseImgs.add(loadAssets("huxu/" + paths[i]));
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
            paths = mContext.getAssets().list("erduo");
            for (int i = 0; i < paths.length; i++) {
                headImgs.add(loadAssets("erduo/" + paths[i]));
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
            paths = mContext.getAssets().list("glass");
            for (int i = 0; i < paths.length; i++) {
                eyeImgs.add(loadAssets("glass/" + paths[i]));
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

    //图片是否加载完全
    public boolean isImgsReady() {
        if (noseImgs.size() < noseLength ||
                headImgs.size() < headLength ||
                eyeImgs.size() < eyeLength ||
                bearImgs.size() < bearLength || mouthImgs.size() < mouthLength) {
            return false;
        }
        return true;
    }

    public void initCurrentImg(FacePart part) {
        switch (part) {
            case EYE:
                if (eyeIndex >= eyeImgs.size()) {
                    eyeIndex = 0;
                }
                eyeImg = eyeImgs.get(eyeIndex);
                break;
            case HEAD:
                if (headIndex >= headImgs.size()) {
                    headIndex = 0;
                }
                headImg = headImgs.get(headIndex);
                break;
            case MOUTH:
                if (mouthIndex >= mouthImgs.size()) {
                    mouthIndex = 0;
                }
                mouthImg = mouthImgs.get(mouthIndex);
                break;
            case NOSE:
                if (noseIndex >= noseImgs.size()) {
                    noseIndex = 0;
                }
                noseImg = noseImgs.get(noseIndex);
                break;
            case BOTTOM:
                if (bearIndex >= bearImgs.size()) {
                    bearIndex = 0;
                }
                bearImg = bearImgs.get(bearIndex);
                break;
            default:
                break;
        }
    }

    //释放bitmap资源
    public void release() {
        noseImgs.clear();
        headImgs.clear();
        eyeImgs.clear();
        bearImgs.clear();
        mouthImgs.clear();
        noseImg = null;
        headImg = null;
        bearImg = null;
        eyeImg = null;
        mouthImg = null;
    }

    enum FacePart {
        EYE, HEAD, MOUTH, NOSE, BOTTOM
    }

    public static void loadImgsFromDir(MaskBeanProxy aMaskBeanProxy, String dir) {
        MaskBean maskBean = aMaskBeanProxy.getMask();
        boolean isReady = false;
        MaskBean.HeadBean head = maskBean.getHead();
        if (head != null) {
            isReady = initPart(aMaskBeanProxy.getHeadImgs(), dir + File.separator + "head", head.getImgCount());
            aMaskBeanProxy.setHeadImg(aMaskBeanProxy.getHeadImgs().get(head.getIndex()));
        }
        Lg.trace("loadhead");
        MaskBean.EyeBean eyeBean = maskBean.getEye();
        if (eyeBean != null) {
            isReady = initPart(aMaskBeanProxy.getEyeImgs(), dir + File.separator + "eye", eyeBean.getImgCount());
            aMaskBeanProxy.setEyeImg(aMaskBeanProxy.getEyeImgs().get(eyeBean.getIndex()));
        }
        Lg.trace("loadeye");
        MaskBean.NoseBean noseBean = maskBean.getNose();
        if (noseBean != null) {
            isReady = initPart(aMaskBeanProxy.getNoseImgs(), dir + File.separator + "nose", noseBean.getImgCount());
            aMaskBeanProxy.setNoseImg(aMaskBeanProxy.getNoseImgs().get(noseBean.getIndex()));
        }
        Lg.trace("loadnose");
        MaskBean.MouthBean mouthBean = maskBean.getMouth();
        if (mouthBean != null) {
            isReady = initPart(aMaskBeanProxy.getMouthImgs(), dir + File.separator + "mouth", mouthBean.getImgCount());
            aMaskBeanProxy.setNoseImg(aMaskBeanProxy.getMouthImgs().get(mouthBean.getIndex()));
        }
        Lg.trace("loadmouth");
        MaskBean.ChinBean chinBean = maskBean.getChin();
        if (chinBean != null) {
            isReady = initPart(aMaskBeanProxy.getChinImgs(), dir + File.separator + "chin", chinBean.getImgCount());
            aMaskBeanProxy.setChinImg(aMaskBeanProxy.getChinImgs().get(chinBean.getIndex()));
        }
        Lg.trace("loadImgsFromDir(BitmapLoader.java:270)-->>" + isReady);
        aMaskBeanProxy.setReady(isReady);
    }

    private static boolean initPart(List<Bitmap> imgs, String aPath, int count) {
        int imgCount = count;
        String path = aPath;
        Lg.trace("initPart(BitmapLoader.java:275)-->>" + path);
        File headFile = new File(path);
        if (headFile.exists() && headFile.isDirectory()) {
            File[] files = headFile.listFiles();
            if (files.length < imgCount) {
                return false;
            }
            for (int i = 0; i < files.length; i++) {
                imgs.add(loadBitmapFromFiles(files[i]));
            }
            return true;
        }
        return false;
    }

    //从file里读取bitmap
    public static Bitmap loadBitmapFromFiles(File file) {
        Bitmap image = null;
        if (!file.exists()) {
            return image;
        }
        Lg.trace("loadBitmapFromFiles(BitmapLoader.java:298)-->>");
        try {
            InputStream is = new FileInputStream(file);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
