package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.Tiles;
import com.whyyu.indoormanagementserver.service.TilesService;
import com.whyyu.indoormanagementserver.util.CommonResult;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/5 10:17
 */
@RestController
@RequestMapping("/Tiles")
public class TileController {
    @Autowired
    TilesService tilesService;

    @PostMapping("/table/data")
    public CommonResult<Page<Tiles>> getPageData(@RequestBody PageParam pageParam) {
        Page<Tiles> resultPage = tilesService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    /**
     * description: 不可以用@RequestBody接受参数哦，因为这里ContentType是'multipart/form-data;'<br></>
     * 不是在RequestBody中的json数据就无法解析<br>
     * date: 2021/8/5 16:23 <br>
     * author: WhyYu <br>
     * @param file 上传文件
     * @return result
     */
    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile file, @RequestParam String name,
                                           @RequestParam String zoom, @RequestParam String row, @RequestParam String col) {
        if (file != null) {
            tilesService.importData(file, name, zoom, row, col);
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }

    /**
     * description: 获得入参对应的瓦片 <br>
     * date: 2021/8/5 15:23 <br>
     * author: WhyYu <br>
     * @param name 瓦片所属的瓦片集名称
     * @param zoom 金字塔层级
     * @param row 行
     * @param colExtension 列以及文件格式，如：2.png
     * @param response 返回的response
     */
    @GetMapping(value = "/{name}/{zoom}/{row}/{colExtension:.+}")
    public void getImg(@PathVariable String name, @PathVariable String zoom, @PathVariable String row, @PathVariable String colExtension, HttpServletResponse response) {
        String path = tilesService.tilesPathBuild(name, zoom, row, colExtension);
        String extension = colExtension.split("\\.")[1];;
        tilesService.getImage(response, path, extension);
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(tilesService.deleteByTilesId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody Tiles tiles) {
        return CommonResult.success(tilesService.addTiles(tiles));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody Tiles tiles) {
        return CommonResult.success(tilesService.updateTiles(tiles));
    }

    @GetMapping("test")
    public CommonResult<List<Tiles>> getAllData() {
        return CommonResult.success(tilesService.findAll());
    }
}
