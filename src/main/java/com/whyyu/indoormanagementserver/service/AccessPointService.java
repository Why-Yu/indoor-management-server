package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.AccessPoint;
import com.whyyu.indoormanagementserver.repo.AccessPointRepo;
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
 * @Date 2021/7/28 11:44
 */
@Service
public class AccessPointService {
    @Autowired
    AccessPointRepo accessPointRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<AccessPoint> saveAll(Iterable<AccessPoint> entities) {
        return accessPointRepo.saveAll(entities);
    }

    public Page<AccessPoint> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return accessPointRepo.findAll(pageable);
    }

    public int deleteByAccessPointId(Integer accessPointId) {
        return accessPointRepo.deleteByAccessPointId(accessPointId);
    }

    public int addAccessPoint(AccessPoint accessPoint) {
        accessPointRepo.save(accessPoint);
        return 1;
    }

    public int updateAccessPoint(AccessPoint accessPoint) {
        return accessPointRepo.updateAccessPoint(accessPoint.getIndex(), accessPoint.getName(), accessPoint.getX(), accessPoint.getY(), accessPoint.getH());
    }

    public List<AccessPoint> findAll() {
        return accessPointRepo.findAll();
    }

    public long count() {
        return accessPointRepo.count();
    }

    public void importData(MultipartFile file) {
        ArrayList<AccessPoint> excelData = new ArrayList<>();
        File parentFile = new File(targetPath + "Ap");
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
            excelData = ExcelReader.readExcel(storingFile, AccessPoint.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveAll(excelData);
    }
}
