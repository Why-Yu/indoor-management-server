package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.Poi;
import com.whyyu.indoormanagementserver.repo.PoiRepo;
import com.whyyu.indoormanagementserver.util.ExcelReader;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/9 14:57
 */
@Service
public class PoiService {
    @Autowired
    PoiRepo poiRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<Poi> saveAll(Iterable<Poi> entities) {
        return poiRepo.saveAll(entities);
    }

    public Page<Poi> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return poiRepo.findAll(pageable);
    }

    public int deleteByPoiId(Integer poiId) {
        return poiRepo.deleteByPoiId(poiId);
    }

    public int addPoi(Poi poi) {
        poiRepo.save(poi);
        return 1;
    }

    public int updatePoi(Poi poi) {
        return poiRepo.updatePoi(poi.getIndex(), poi.getProvince(), poi.getCity(), poi.getArea(), poi.getType(), poi.getName(),
                poi.getLongitude(), poi.getLatitude());
    }

    public List<Poi> findAll() {
        return poiRepo.findAll();
    }

    public long count() {
        return poiRepo.count();
    }

    public void importData(MultipartFile file) {
        ArrayList<Poi> excelData = new ArrayList<>();
        File parentFile = new File(targetPath + "Poi");
        // 先检查存储目录是否存在
        if ( !parentFile.exists() ) {
            parentFile.mkdirs();
        }
        // 生成新的存储文件对象
        String fileName = file.getOriginalFilename();
        File storingFile = new File(parentFile.getPath() + "/" + fileName);
        // 读取excel
        try {
            file.transferTo(storingFile);
            excelData = ExcelReader.readExcel(storingFile, Poi.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveAll(excelData);
    }
}
