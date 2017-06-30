package com.iflytek.facedemo.util;

/**
 * Created by xianshang.liu on 2017/6/30.
 */

public class MathUtil {

    public static MyPoint getThirdPointLeft(MyPoint aOne, MyPoint aTwo, float p) {
        float cos = (float) (Math.abs((aOne.y - aTwo.y)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float sin = (float) (Math.abs((aOne.x - aTwo.x)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float deltaX = Math.abs(p * cos * 3f);
        float deltaY = Math.abs(p * sin * 3f);
        float x3 = 0;
        float y3 = 0;
        if (aOne.y>aTwo.y) {
            x3 = aOne.x + deltaX;
        }else{
            x3 = aOne.x - deltaX;
        }
        y3 = aOne.y - deltaY;
        return new MyPoint(x3, y3);
    }

    public static MyPoint getThirdPointRight(MyPoint aOne, MyPoint aTwo, float p) {
        float cos = (float) (Math.abs((aOne.y - aTwo.y)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float sin = (float) (Math.abs((aOne.x - aTwo.x)) / Math.sqrt((aOne.x - aTwo.x) * (aOne.x - aTwo.x) + (aOne.y - aTwo.y) * (aOne.y - aTwo.y)));
        float deltaX = Math.abs(p * cos * 3f);
        float deltaY = Math.abs(p * sin * 3f);
        float x3 = 0;
        float y3 = 0;
        if (aOne.y>aTwo.y) {
            x3 = aTwo.x + deltaX;
        }else{
            x3 = aTwo.x - deltaX;
        }
        y3 = aTwo.y - deltaY;
        return new MyPoint(x3, y3);
    }
}
