package com.whyyu.indoormanagementserver.service;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.TdTiles;
import com.whyyu.indoormanagementserver.repo.TdTilesRepo;
import com.whyyu.indoormanagementserver.util.JsonReader;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/5 21:58
 */
@Service
public class TdTilesService {
    @Autowired
    TdTilesRepo tdTilesRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<TdTiles> saveAll(Iterable<TdTiles> entities) {
        return tdTilesRepo.saveAll(entities);
    }

    public Page<TdTiles> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return tdTilesRepo.findAll(pageable);
    }

    public int deleteByTdTilesId(Integer tdTilesId) {
        return tdTilesRepo.deleteByTdTilesId(tdTilesId);
    }

    public void addTdTiles(String name, Double longitude, Double latitude) {
        tdTilesRepo.save(new TdTiles(name, targetPath + "TdTiles/" + name, longitude, latitude));
    }

    public int addTdTiles(TdTiles tdTiles) {
        tdTilesRepo.save(tdTiles);
        return 1;
    }

    public int updateTdTiles(TdTiles tdTiles) {
        return tdTilesRepo.updateTdTiles(tdTiles.getIndex(), tdTiles.getName(), tdTiles.getPath(), tdTiles.getLongitude(), tdTiles.getLatitude());
    }

    public List<TdTiles> findAll() {
        return tdTilesRepo.findAll();
    }

    public long count() {
        return tdTilesRepo.count();
    }


    public void importData(MultipartFile file, String name) {
        File parentFile = new File(targetPath + "TdTiles/" + name);
        // ?????????????????????????????????
        if ( !parentFile.exists() ) {
            parentFile.mkdirs();
        }
        // ??????????????????????????????
        // ???????????????fileName????????????/?????????????????????????????????/????????????File???windows?????????\
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            int startIndex = fileName.indexOf("/");
            int endIndex = fileName.lastIndexOf("/");
            // ???????????????????????????
            if ( startIndex != endIndex ) {
                File storingDirectory = new File(parentFile.getPath() + "/" + fileName.substring(startIndex + 1, endIndex));
                if ( !storingDirectory.exists() ) {
                    storingDirectory.mkdirs();
                }
            }
            fileName = fileName.substring(fileName.indexOf("/") + 1);
        }
        File storingFile = new File(parentFile.getPath() + "/" + fileName);
        try {
            file.transferTo(storingFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String ,Double> getTileJson(String name) {
        HashMap<String ,Double> resultMap = new HashMap<>();
        ArrayList<?> tempList;
        String tileSetPath = targetPath + "TdTiles/" + name + "/" + "tileset.json";
        JSONObject jsonObject = JsonReader.readJsonFile(tileSetPath);
        tempList = jsonObject.getJSONObject("root").getJSONObject("boundingVolume").getObject("region", ArrayList.class);

        resultMap.put("lng", Math.toDegrees(Double.parseDouble(tempList.get(0).toString())));
        resultMap.put("lat", Math.toDegrees(Double.parseDouble(tempList.get(1).toString())));
        return resultMap;
    }
}
