package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.BlueTooth;
import com.whyyu.indoormanagementserver.service.BlueToothService;
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
 * @Date 2021/7/28 12:09
 */
@RestController
@RequestMapping("/Bluetooth")
public class BlueToothController {
    @Autowired
    BlueToothService blueToothService;

    @PostMapping("/table/data")
    public CommonResult<Page<BlueTooth>> getPageData(@RequestBody PageParam pageParam) {
        Page<BlueTooth> resultPage = blueToothService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            blueToothService.importData(file);
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(blueToothService.deleteByBlueToothId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody BlueTooth blueTooth) {
        return CommonResult.success(blueToothService.addBlueTooth(blueTooth));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody BlueTooth blueTooth) {
        return CommonResult.success(blueToothService.updateBlueTooth(blueTooth));
    }

    @GetMapping("test")
    public CommonResult<List<BlueTooth>> getAllData() {
        return CommonResult.success(blueToothService.findAll());
    }
}
