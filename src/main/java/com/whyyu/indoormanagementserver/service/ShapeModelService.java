package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.ShapeModel;
import com.whyyu.indoormanagementserver.repo.ShapeModelRepo;
import com.whyyu.indoormanagementserver.util.PageParam;
import com.whyyu.indoormanagementserver.util.ShapeReader;
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
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/3 17:16
 */
@Service
public class ShapeModelService {
    @Autowired
    ShapeModelRepo shapeModelRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<ShapeModel> saveAll(Iterable<ShapeModel> entities) {
        return shapeModelRepo.saveAll(entities);
    }

    public Page<ShapeModel> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return shapeModelRepo.findAll(pageable);
    }

    public long count() {
        return shapeModelRepo.count();
    }

    public void importData(MultipartFile file, String buildId, String floor) {
        File parentFile = new File(targetPath + "IndoorTopo");
        ArrayList<ShapeModel> modelList = new ArrayList<>();
        // 先检查存储目录是否存在
        if ( !parentFile.exists() ) {
            parentFile.mkdirs();
        }
        // 生成新的存储文件对象
        String fileName = file.getOriginalFilename();
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(parentFile.getPath()).append("/")
                .append(buildId).append("-")
                .append(floor).append(fileName);
        File storingFile = new File(pathBuilder.toString());
        // 把临时文件转移到存储目标中
        try {
            file.transferTo(storingFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 读取shapefile文件并且把相关属性存到数据库中
        if(fileName.endsWith(".shp")) {
            modelList = ShapeReader.readShapeFile(storingFile, buildId, floor);
            saveAll(modelList);
        }
    }
}
