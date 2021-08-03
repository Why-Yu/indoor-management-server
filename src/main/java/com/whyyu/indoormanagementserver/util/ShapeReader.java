package com.whyyu.indoormanagementserver.util;

import com.whyyu.indoormanagementserver.entity.ShapeModel;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author WhyYu
 * @Description 导入室内拓扑数据 Indoor
 * @Date 2021/8/2 21:24
 */
public class ShapeReader {
    public static ArrayList<ShapeModel> readShapeFile(File shapeFile, String buildId, String floor) {
        ArrayList<ShapeModel> modelList = new ArrayList<>();
        try {
            modelList = getShapeFileData(shapeFile, buildId, floor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelList;
    }
    private static ArrayList<ShapeModel> getShapeFileData(File file, String buildId, String floor) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("url", file.toURI().toURL());
        ArrayList<ShapeModel> models = new ArrayList<>();
        String prefix = buildId + "-" + floor + "-";

        DataStore dataStore = DataStoreFinder.getDataStore(map);
        //字符转码，防止中文乱码
        ((ShapefileDataStore) dataStore).setCharset(StandardCharsets.UTF_8);
        String typeName = dataStore.getTypeNames()[0];
        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures();
        FeatureIterator<SimpleFeature> features = collection.features();
        // 把每个feature转换成实体类对象
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            ShapeModel model = new ShapeModel(
                    buildId,
                    prefix + feature.getAttribute("BeginId").toString(),
                    Double.parseDouble(feature.getAttribute("BeginX").toString()),
                    Double.parseDouble(feature.getAttribute("BeginY").toString()),
                    prefix + feature.getAttribute("EndId").toString(),
                    Double.parseDouble(feature.getAttribute("EndX").toString()),
                    Double.parseDouble(feature.getAttribute("EndY").toString()),
                    floor
            );
            models.add(model);
        }
        // 关闭FeatureIterator以及DataStore,防止之后再读取，出现The following locker still has a lock: read on file错误
        // 这里两个都需要关闭，之前有一个未关闭会报错
        features.close();
        dataStore.dispose();
        return models;
    }
}
