package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.PointCloud;
import com.whyyu.indoormanagementserver.service.PointCloudService;
import com.whyyu.indoormanagementserver.util.CommonResult;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/19 20:55
 */
@RestController
@RequestMapping("/PointCloud")
public class PointCloudController {
    @Autowired
    PointCloudService pointCloudService;

    @PostMapping("/table/data")
    public CommonResult<Page<PointCloud>> getPageData(@RequestBody PageParam pageParam) {
        Page<PointCloud> resultPage = pointCloudService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile file, @RequestParam String name,
                                           @RequestParam Double longitude, @RequestParam Double latitude) {
        if (file != null) {
            pointCloudService.importData(file, name, longitude, latitude);
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(pointCloudService.deleteByPointCloudId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody PointCloud pointCloud) {
        return CommonResult.success(pointCloudService.addPointCloud(pointCloud));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody PointCloud pointCloud) {
        return CommonResult.success(pointCloudService.updatePointCloud(pointCloud));
    }

    @GetMapping("test")
    public CommonResult<List<PointCloud>> getAllData() {
        return CommonResult.success(pointCloudService.findAll());
    }
}
