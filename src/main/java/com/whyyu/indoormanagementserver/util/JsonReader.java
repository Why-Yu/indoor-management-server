package com.whyyu.indoormanagementserver.util;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/6 16:09
 */
public class JsonReader {
    public static JSONObject readJsonFile(String filePath){
        BufferedReader reader = null;
        String readJson = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
            reader = new BufferedReader(inputStreamReader);
            while ((readJson = reader.readLine()) != null){
                stringBuilder.append(readJson);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        // 获取json
        JSONObject jsonObject = JSONObject.parseObject(stringBuilder.toString());
        stringBuilder.setLength(0);
        return jsonObject;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = readJsonFile("E:\\storage\\TdTiles\\sample\\tileset.json");
        System.out.println(jsonObject.getJSONObject("root").getJSONObject("boundingVolume").getObject("region", ArrayList.class));
    }
}
