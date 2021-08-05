package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.Tiles;
import com.whyyu.indoormanagementserver.repo.TilesRepo;
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
import java.io.*;
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/5 10:23
 */
@Service
public class TilesService {
    @Autowired
    TilesRepo tilesRepo;
    @Value("${file.targetPath}")
    private String targetPath;

    public List<Tiles> saveAll(Iterable<Tiles> entities) {
        return tilesRepo.saveAll(entities);
    }

    public Page<Tiles> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return tilesRepo.findAll(pageable);
    }

    public int deleteByTilesId(Integer tilesId) {
        return tilesRepo.deleteByTilesId(tilesId);
    }

    public int addTiles(Tiles tiles) {
        tilesRepo.save(tiles);
        return 1;
    }

    public int updateTiles(Tiles tiles) {
        return tilesRepo.updateTiles(tiles.getIndex(), tiles.getName(), tiles.getZoom(), tiles.getRow(), tiles.getCol());
    }

    public List<Tiles> findAll() {
        return tilesRepo.findAll();
    }

    public long count() {
        return tilesRepo.count();
    }

    public void importData(MultipartFile file, String name, String zoom, String row, String col) {
        File parentFile = new File(targetPath + "Tiles");
        // 先检查存储目录是否存在
        if ( !parentFile.exists() ) {
            parentFile.mkdirs();
        }
        // 生成新的存储文件对象
        // 这里fileName就和colExtension是一样的
        String fileName = file.getOriginalFilename();
        String storingPath = tilesPathBuild(name, zoom, row, fileName);
        File storingFile = new File(storingPath);
        // 把临时文件转移到存储目标中
        try {
            file.transferTo(storingFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tilesRepo.save(new Tiles(name, zoom, row, col));
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
            System.out.println("瓦片请求出错");
        }
    }


    public String tilesPathBuild(String name, String zoom, String row, String colExtension) {
        StringBuilder pathBuilder = new StringBuilder(targetPath);
        pathBuilder.append("Tiles/").append(name).append("/")
                .append(zoom).append("/").append(row)
                .append("/").append(colExtension);
        return pathBuilder.toString();
    }

    @Deprecated
    // 用这种方式也行，但缺点就是比较简陋，不如用别人直接封装好的ImageIO
    private void anotherGetImage(HttpServletResponse response) {
        byte[] data = null;
        try {
            InputStream in = new FileInputStream("C:\\Users\\Dell\\Desktop\\Tiles\\2\\3\\1.png");
            data = new byte[in.available()];
            in.read(data);
            in.close();
            response.setContentType("image/png");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
