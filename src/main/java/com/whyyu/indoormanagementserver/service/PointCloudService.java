package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.PointCloud;
import com.whyyu.indoormanagementserver.repo.PointCloudRepo;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/19 20:51
 */
@Service
public class PointCloudService {
    @Autowired
    PointCloudRepo pointCloudRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<PointCloud> saveAll(Iterable<PointCloud> entities) {
        return pointCloudRepo.saveAll(entities);
    }

    public Page<PointCloud> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return pointCloudRepo.findAll(pageable);
    }

    public int deleteByPointCloudId(Integer pointCloudId) {
        return pointCloudRepo.deleteByPointCloudId(pointCloudId);
    }

    public int addPointCloud(PointCloud pointCloud) {
        pointCloudRepo.save(pointCloud);
        return 1;
    }

    public int updatePointCloud(PointCloud pointCloud) {
        return pointCloudRepo.updatePointCloud(pointCloud.getIndex(), pointCloud.getName(), pointCloud.getPath(), pointCloud.getLongitude(), pointCloud.getLatitude());
    }

    public List<PointCloud> findAll() {
        return pointCloudRepo.findAll();
    }

    public long count() {
        return pointCloudRepo.count();
    }

    public PointCloud findByIndex(Integer pointCloudIndex) {
        return pointCloudRepo.findByIndex(pointCloudIndex);
    }

    public void importData(MultipartFile file, String pointCloudName, Double longitude, Double latitude) {
        File parentFile = new File(targetPath + "PointCloud/" + pointCloudName);
        // 先检查存储目录是否存在
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        // 生成新的存储文件对象
        String fileName = file.getOriginalFilename();
        // 这里使用\\是为了和windows下\表示分隔符形成一致
        String storingPath = parentFile.getPath() + "\\" + fileName;
        File storingFile = new File(storingPath);

        try {
            file.transferTo(storingFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pointCloudRepo.save(new PointCloud(pointCloudName, storingPath, longitude, latitude));
    }
}
