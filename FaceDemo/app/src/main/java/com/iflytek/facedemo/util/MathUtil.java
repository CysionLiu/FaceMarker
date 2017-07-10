package com.iflytek.facedemo.util;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by xianshang.liu on 2017/6/30.
 */

public class MathUtil {
    //求取两点和一个距离确定的矩形的第三个点-靠近左边
    public static MyPoint getThirdPointLeft(MyPoint aOne, MyPoint aTwo, float p) {
        float cos = (float) (Math.abs((aOne.y - aTwo.y)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float sin = (float) (Math.abs((aOne.x - aTwo.x)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float deltaX = Math.abs(p * cos);
        float deltaY = Math.abs(p * sin);
        float x3 = 0;
        float y3 = 0;
        if (aOne.y > aTwo.y) {
            x3 = aOne.x + deltaX;
        } else {
            x3 = aOne.x - deltaX;
        }
        y3 = aOne.y - deltaY;
        return new MyPoint(x3, y3);
    }

    //求取两点和一个距离确定的矩形的第三个点--靠近右边
    public static MyPoint getThirdPointRight(MyPoint aOne, MyPoint aTwo, float p) {
        float cos = (float) (Math.abs((aOne.y - aTwo.y)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float sin = (float) (Math.abs((aOne.x - aTwo.x)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float deltaX = Math.abs(p * cos);
        float deltaY = Math.abs(p * sin);
        float x3 = 0;
        float y3 = 0;
        if (aOne.y > aTwo.y) {
            x3 = aTwo.x + deltaX;
        } else {
            x3 = aTwo.x - deltaX;
        }
        y3 = aTwo.y - deltaY;
        return new MyPoint(x3, y3);
    }
    //求取两点和一个距离确定的矩形的第三个点-靠近左边
    public static MyPoint getThirdPointLeftUp(MyPoint aOne, MyPoint aTwo, float p) {
        float cos = (float) (Math.abs((aOne.y - aTwo.y)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float sin = (float) (Math.abs((aOne.x - aTwo.x)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float deltaX = Math.abs(p * cos);
        float deltaY = Math.abs(p * sin);
        float x3 = 0;
        float y3 = 0;
        if (aOne.y > aTwo.y) {
            x3 = aOne.x - deltaX;
        } else {
            x3 = aOne.x + deltaX;
        }
        y3 = aOne.y + deltaY;
        return new MyPoint(x3, y3);
    }

    //求取两点和一个距离确定的矩形的第三个点--靠近右边
    public static MyPoint getThirdPointRightDown(MyPoint aOne, MyPoint aTwo, float p) {
        float cos = (float) (Math.abs((aOne.y - aTwo.y)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float sin = (float) (Math.abs((aOne.x - aTwo.x)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float deltaX = Math.abs(p * cos);
        float deltaY = Math.abs(p * sin);
        float x3 = 0;
        float y3 = 0;
        if (aOne.y > aTwo.y) {
            x3 = aTwo.x - deltaX;
        } else {
            x3 = aTwo.x + deltaX;
        }
        y3 = aTwo.y + deltaY;
        return new MyPoint(x3, y3);
    }
    //求取两点之间距离
    public static float getDistance(MyPoint aOne, MyPoint aTwo) {
        float deltaX = aOne.x - aTwo.x;
        float deltaY = aOne.y - aTwo.y;
        float square = deltaX * deltaX + deltaY * deltaY;
        return (float) Math.sqrt(square);
    }

    //求取两点的中点
    public static MyPoint getMiddle(MyPoint aOne, MyPoint aTwo) {
        float targetX = aOne.x + aTwo.x;
        float targetY = aOne.y + aTwo.y;
        targetX = targetX / 2;
        targetY = targetY / 2;
        return new MyPoint(targetX, targetY);
    }

    //求取两点相对水平线的夹角
    public static float getDegree(MyPoint aOne, MyPoint aTwo) {
        float tanEyes = 1.0f * (aOne.y - aTwo.y) / (aOne.x - aTwo.x);
        return (float) Math.toDegrees(Math.atan(tanEyes));
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


}
