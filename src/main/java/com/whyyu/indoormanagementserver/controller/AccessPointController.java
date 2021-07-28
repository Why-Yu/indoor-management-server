package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.AccessPoint;
import com.whyyu.indoormanagementserver.service.AccessPointService;
import com.whyyu.indoormanagementserver.util.CommonResult;
import com.whyyu.indoormanagementserver.util.ExcelReader;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/7/28 11:52
 */
@RestController
@RequestMapping("/Ap")
public class AccessPointController {
    @Autowired
    AccessPointService accessPointService;

    @PostMapping("/table/data")
    public CommonResult<Page<AccessPoint>> getPageData(@RequestBody PageParam pageParam) {
        Page<AccessPoint> resultPage = accessPointService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestBody JSONObject jsonParam) {
        try {
            ArrayList<AccessPoint> excelData = ExcelReader.read(jsonParam.getString("filePath"), AccessPoint.class);
            accessPointService.saveAll(excelData);
        } catch ( Exception ignored) {
        }
        return CommonResult.success("import success");
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(accessPointService.deleteByAccessPointId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody AccessPoint accessPoint) {
        return CommonResult.success(accessPointService.addAccessPoint(accessPoint));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody AccessPoint accessPoint) {
        return CommonResult.success(accessPointService.updateAccessPoint(accessPoint));
    }

    @GetMapping("test")
    public CommonResult<List<AccessPoint>> getAllData() {
        return CommonResult.success(accessPointService.findAll());
    }

}
