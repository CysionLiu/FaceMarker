package com.iflytek.facedemo.util;

import android.text.TextUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by xianshang.liu on 2017/7/10.
 */

public class JsonUtils {

    //从文件导出字符串
    public static String getJsonFromFile(String absolutePath) {
        String resultJson = "";
        if (TextUtils.isEmpty(absolutePath)) {
            return resultJson;
        }
        File jsonFile = new File(absolutePath);
        if (!jsonFile.exists()) {
            return resultJson;
        }
        try {
            FileReader reader = new FileReader(absolutePath);
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[1024];
            int i = 0;
            while ((i = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, i);
            }
            resultJson = builder.toString();
            return resultJson;
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        return resultJson;
    }

}
