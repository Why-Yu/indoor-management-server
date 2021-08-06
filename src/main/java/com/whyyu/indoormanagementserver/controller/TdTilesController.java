package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.TdTiles;
import com.whyyu.indoormanagementserver.service.TdTilesService;
import com.whyyu.indoormanagementserver.util.CommonResult;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/5 21:15
 */
@RestController
@RequestMapping("/TdTiles")
public class TdTilesController {
    @Autowired
    TdTilesService tdTilesService;

    @PostMapping("/table/data")
    public CommonResult<Page<TdTiles>> getPageData(@RequestBody PageParam pageParam) {
        Page<TdTiles> resultPage = tdTilesService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile[] files, @RequestParam String name) {
        if (files != null) {
            for (MultipartFile file : files) {
                tdTilesService.importData(file, name);
            }
            HashMap<String ,Double> resultMap = new HashMap<>();
            resultMap = tdTilesService.getTileJson(name);
            tdTilesService.addTdTiles(name, resultMap.get("lng"), resultMap.get("lat"));
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(tdTilesService.deleteByTdTilesId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody TdTiles tdTiles) {
        return CommonResult.success(tdTilesService.addTdTiles(tdTiles));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody TdTiles tdTiles) {
        return CommonResult.success(tdTilesService.updateTdTiles(tdTiles));
    }

    @GetMapping("test")
    public CommonResult<List<TdTiles>> getAllData() {
        return CommonResult.success(tdTilesService.findAll());
    }
}
