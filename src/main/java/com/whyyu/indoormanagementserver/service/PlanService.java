package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.Plan;
import com.whyyu.indoormanagementserver.repo.PlanRepo;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/6 17:30
 */
@Service
public class PlanService {
    @Autowired
    PlanRepo planRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<Plan> saveAll(Iterable<Plan> entities) {
        return planRepo.saveAll(entities);
    }

    public Page<Plan> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return planRepo.findAll(pageable);
    }

    public int deleteByPlanId(Integer planId) {
        return planRepo.deleteByPlanId(planId);
    }

    public int addPlan(Plan plan) {
        planRepo.save(plan);
        return 1;
    }

    public int updatePlan(Plan plan) {
        return planRepo.updatePlan(plan.getIndex(), plan.getName(), plan.getPath());
    }

    public List<Plan> findAll() {
        return planRepo.findAll();
    }

    public long count() {
        return planRepo.count();
    }

    public Plan findByIndex(Integer planIndex) {
        return planRepo.findByIndex(planIndex);
    }

    public void importData(MultipartFile file, String planName, Double longitude, Double latitude) {
        File parentFile = new File(targetPath + "Plan/" + planName);
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
        planRepo.save(new Plan(planName, storingPath, longitude, latitude));
    }

    public void getImage(HttpServletResponse response, String path, String extension) {
        try {
            InputStream inputStream = new FileInputStream(path);
            BufferedImage br = ImageIO.read(inputStream);
            ImageIO.write(br, extension, response.getOutputStream());
            br.flush();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("平面缩略图请求出错");
        }
    }
}
