package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.Poi;
import com.whyyu.indoormanagementserver.service.PoiService;
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
 * @Date 2021/8/9 15:31
 */
@RestController
@RequestMapping("/Poi")
public class PoiController {
    @Autowired
    PoiService poiService;

    @PostMapping("/table/data")
    public CommonResult<Page<Poi>> getPageData(@RequestBody PageParam pageParam) {
        Page<Poi> resultPage = poiService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            poiService.importData(file);
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(poiService.deleteByPoiId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody Poi poi) {
        return CommonResult.success(poiService.addPoi(poi));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody Poi poi) {
        return CommonResult.success(poiService.updatePoi(poi));
    }

    @GetMapping("test")
    public CommonResult<List<Poi>> getAllData() {
        return CommonResult.success(poiService.findAll());
    }
}
