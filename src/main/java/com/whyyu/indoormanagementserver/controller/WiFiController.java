package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.WiFi;
import com.whyyu.indoormanagementserver.service.WiFiService;
import com.whyyu.indoormanagementserver.util.CommonResult;
import com.whyyu.indoormanagementserver.util.ExcelReader;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        System.out.println(pageParam);
        Page<WiFi> resultPage = wifiService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestBody JSONObject jsonParam) {
        try {
            ArrayList<WiFi> excelData = ExcelReader.read(jsonParam.getString("filePath"), WiFi.class);
            System.out.println(excelData);
            wifiService.saveAll(excelData);
        } catch ( Exception ignored) {
        }
        return CommonResult.success("import success");
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
