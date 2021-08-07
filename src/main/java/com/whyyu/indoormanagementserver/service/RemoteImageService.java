package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.RemoteImage;
import com.whyyu.indoormanagementserver.repo.RemoteImageRepo;
import com.whyyu.indoormanagementserver.util.PageParam;
import com.whyyu.indoormanagementserver.util.TarGzReader;
import com.whyyu.indoormanagementserver.util.TxtReader;
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
import java.util.HashMap;
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/6 22:27
 */
@Service
public class RemoteImageService {
    @Autowired
    RemoteImageRepo remoteImageRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<RemoteImage> saveAll(Iterable<RemoteImage> entities) {
        return remoteImageRepo.saveAll(entities);
    }

    public Page<RemoteImage> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return remoteImageRepo.findAll(pageable);
    }

    public int deleteByRemoteImageId(Integer remoteImageId) {
        return remoteImageRepo.deleteByRemoteImageId(remoteImageId);
    }

    public int addRemoteImage(RemoteImage remoteImage) {
        remoteImageRepo.save(remoteImage);
        return 1;
    }

    public int updateRemoteImage(RemoteImage remoteImage) {
        return remoteImageRepo.updateRemoteImage(remoteImage.getIndex(), remoteImage.getName(), remoteImage.getBand(),
                remoteImage.getRowIndex(), remoteImage.getDate(), remoteImage.getCloud(), remoteImage.getLongitude(),
                remoteImage.getLatitude());
    }

    public List<RemoteImage> findAll() {
        return remoteImageRepo.findAll();
    }

    public long count() {
        return remoteImageRepo.count();
    }

    public RemoteImage findByIndex(Integer index) {
        return remoteImageRepo.findByIndex(index);
    }

    public void importData(MultipartFile file) {
        File parentFile = new File(targetPath + "RemoteImage");
        // 先检查存储目录是否存在
        if ( !parentFile.exists() ) {
            parentFile.mkdirs();
        }
        // 生成新的存储文件对象
        String fileName = file.getOriginalFilename();
        File storingFile = new File(parentFile.getPath() + "/" + fileName);
        try {
            file.transferTo(storingFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解压.tar.gz并提取出两个需要的文件然后存储
        HashMap<String, String> pathMap = TarGzReader.readTarGz(storingFile, targetPath + "RemoteImage");
        // 对提取出的元数据文件进行解析
        RemoteImage remoteImage = TxtReader.readTxt(new File(pathMap.get("Meta")));
        // 获得影像名称
        String imageName = remoteImage.getName();
        // 构建.png缩略图
        try {
            BufferedImage image = ImageIO.read(new File(pathMap.get("Thumbnail")));
            // 图片放缩
            image = TarGzReader.resize(image, 0.04);
            if (!ImageIO.write(image, "png", new File(targetPath + "RemoteImage/" + imageName + ".png"))) {
                System.out.println("image not written");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        remoteImageRepo.save(remoteImage);
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
            System.out.println("缩略图请求出错");
        }
    }

    public String remoteImagePathBuild(String name, String extension) {
        StringBuilder pathBuilder = new StringBuilder(targetPath);
        pathBuilder.append("RemoteImage/").append(name)
                .append(".").append(extension);
        return pathBuilder.toString();
    }
}
