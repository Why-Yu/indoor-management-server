package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.Dem;
import com.whyyu.indoormanagementserver.repo.DemRepo;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/9 16:21
 */
@Service
public class DemService {
    @Autowired
    DemRepo demRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<Dem> saveAll(Iterable<Dem> entities) {
        return demRepo.saveAll(entities);
    }

    public Page<Dem> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return demRepo.findAll(pageable);
    }

    public int deleteByDemId(Integer demId) {
        return demRepo.deleteByDemId(demId);
    }

    public int addDem(Dem dem) {
        demRepo.save(dem);
        return 1;
    }

    public int updateDem(Dem dem) {
        return demRepo.updateDem(dem.getIndex(), dem.getName(), dem.getBand(), dem.getRowIndex(), dem.getLongitude(), dem.getLatitude());
    }

    public List<Dem> findAll() {
        return demRepo.findAll();
    }

    public long count() {
        return demRepo.count();
    }

    public void importData(MultipartFile file, Dem dem) {
        File parentFile = new File(targetPath + "Dem");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        demRepo.save(dem);
    }
}
