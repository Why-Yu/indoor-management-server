package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.WiFi;
import com.whyyu.indoormanagementserver.service.WiFiService;
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
 * @Date 2021/7/26 17:38
 */
@RestController
@RequestMapping("/Wifi")
public class WiFiController {
    @Autowired
    WiFiService wifiService;

    @PostMapping("/table/data")
    public CommonResult<Page<WiFi>> getPageData(@RequestBody PageParam pageParam) {
        Page<WiFi> resultPage = wifiService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile file) {
        // 由于element-ui的el-upload在同时选中多个文件时，也是分别对每个文件单独调用一次此接口，所以这里不需要MultipartFile[]
        if (file != null) {
            wifiService.importData(file);
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(wifiService.deleteByWiFiId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody WiFi wifi) {
        return CommonResult.success(wifiService.addWiFi(wifi));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody WiFi wifi) {
        return CommonResult.success(wifiService.updateWiFi(wifi));
    }

    @GetMapping("test")
    public CommonResult<List<WiFi>> getAllData() {
        return CommonResult.success(wifiService.findAll());
    }
}
