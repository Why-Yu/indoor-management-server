package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.BlueTooth;
import com.whyyu.indoormanagementserver.repo.BlueToothRepo;
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
public class BlueToothService {
    @Autowired
    BlueToothRepo blueToothRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<BlueTooth> saveAll(Iterable<BlueTooth> entities) {
        return blueToothRepo.saveAll(entities);
    }

    public Page<BlueTooth> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return blueToothRepo.findAll(pageable);
    }

    public int deleteByBlueToothId(Integer blueToothId) {
        return blueToothRepo.deleteByBlueToothId(blueToothId);
    }

    public int addBlueTooth(BlueTooth blueTooth) {
        blueToothRepo.save(blueTooth);
        return 1;
    }

    public int updateBlueTooth(BlueTooth blueTooth) {
        return blueToothRepo.updateBlueTooth(blueTooth.getIndex(), blueTooth.getName(), blueTooth.getX(), blueTooth.getY(), blueTooth.getH());
    }

    public List<BlueTooth> findAll() {
        return blueToothRepo.findAll();
    }

    public long count() {
        return blueToothRepo.count();
    }

    public void importData(MultipartFile file) {
        ArrayList<BlueTooth> excelData = new ArrayList<>();
        File parentFile = new File(targetPath + "BlueTooth");
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
            excelData = ExcelReader.readExcel(storingFile, BlueTooth.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveAll(excelData);
    }
}
