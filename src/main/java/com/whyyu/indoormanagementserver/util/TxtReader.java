package com.whyyu.indoormanagementserver.util;

import com.whyyu.indoormanagementserver.entity.RemoteImage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/**
 * @author WhyYu
 * @Description 解析LANDSAT8遥感影像的元数据文件，针对前端需要展示的数据提取出对应字段的值
 * @Date 2021/8/7 16:12
 */
public class TxtReader {
    public static RemoteImage readTxt(File file) {
        BufferedReader reader = null;
        HashMap<String, String> resultMap = new HashMap<>();
        HashMap<String, String> tempMap = new HashMap<>();
        try {
            String temp = null;
            reader = new BufferedReader(new FileReader(file));
            while ((temp = reader.readLine()) != null) {
                int mark = temp.indexOf("=");
                if (mark > 0) {
                    String key = temp.substring(0, mark).trim();
                    String value = temp.substring(mark + 1).trim();
                    tempMap.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new RemoteImage(tempMap.get("LANDSAT_SCENE_ID").replace("\"", ""), Integer.parseInt(tempMap.get("WRS_PATH")),
                Integer.parseInt(tempMap.get("WRS_ROW")), tempMap.get("FILE_DATE").substring(0, tempMap.get("FILE_DATE").indexOf("T")),
                Float.parseFloat(tempMap.get("CLOUD_COVER")), Double.parseDouble(tempMap.get("CORNER_UL_LON_PRODUCT")),
                Double.parseDouble(tempMap.get("CORNER_UL_LAT_PRODUCT")));
    }
}
