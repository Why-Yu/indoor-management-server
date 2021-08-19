package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.Panorama;
import com.whyyu.indoormanagementserver.repo.PanoramaRepo;
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
 * @Date 2021/8/19 17:52
 */
@Service
public class PanoramaService {
    @Autowired
    PanoramaRepo panoramaRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<Panorama> saveAll(Iterable<Panorama> entities) {
        return panoramaRepo.saveAll(entities);
    }

    public Page<Panorama> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return panoramaRepo.findAll(pageable);
    }

    public int deleteByPanoramaId(Integer panoramaId) {
        return panoramaRepo.deleteByPanoramaId(panoramaId);
    }

    public int addPanorama(Panorama panorama) {
        panoramaRepo.save(panorama);
        return 1;
    }

    public int updatePanorama(Panorama panorama) {
        return panoramaRepo.updatePanorama(panorama.getIndex(), panorama.getName(), panorama.getPath(), panorama.getLongitude(), panorama.getLatitude());
    }

    public List<Panorama> findAll() {
        return panoramaRepo.findAll();
    }

    public long count() {
        return panoramaRepo.count();
    }

    public Panorama findByIndex(Integer panoramaIndex) {
        return panoramaRepo.findByIndex(panoramaIndex);
    }

    public void importData(MultipartFile file, String panoramaName, Double longitude, Double latitude) {
        File parentFile = new File(targetPath + "Panorama/" + panoramaName);
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
        panoramaRepo.save(new Panorama(panoramaName, storingPath, longitude, latitude));
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
            System.out.println("全景缩略图请求出错");
        }
    }
}
