package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.Panorama;
import com.whyyu.indoormanagementserver.service.PanoramaService;
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
 * @Date 2021/8/19 17:46
 */
@RestController
@RequestMapping("/Panorama")
public class PanoramaController {
    @Autowired
    PanoramaService panoramaService;

    @PostMapping("/table/data")
    public CommonResult<Page<Panorama>> getPageData(@RequestBody PageParam pageParam) {
        Page<Panorama> resultPage = panoramaService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile file, @RequestParam String name,
                                           @RequestParam Double longitude, @RequestParam Double latitude) {
        if (file != null) {
            panoramaService.importData(file, name, longitude, latitude);
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }

    @GetMapping(value = "/{index}")
    public void getImg(@PathVariable Integer index, HttpServletResponse response) {
        Panorama panorama = panoramaService.findByIndex(index);
        panoramaService.getImage(response, panorama.getPath(), "jpeg");
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(panoramaService.deleteByPanoramaId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody Panorama panorama) {
        return CommonResult.success(panoramaService.addPanorama(panorama));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody Panorama panorama) {
        return CommonResult.success(panoramaService.updatePanorama(panorama));
    }

    @GetMapping("test")
    public CommonResult<List<Panorama>> getAllData() {
        return CommonResult.success(panoramaService.findAll());
    }
}
