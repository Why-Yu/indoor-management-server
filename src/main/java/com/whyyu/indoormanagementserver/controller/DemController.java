package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.Dem;
import com.whyyu.indoormanagementserver.service.DemService;
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
 * @Date 2021/8/5 10:15
 */
@RestController
@RequestMapping("/Dem")
public class DemController {
    @Autowired
    DemService demService;

    @PostMapping("/table/data")
    public CommonResult<Page<Dem>> getPageData(@RequestBody PageParam pageParam) {
        Page<Dem> resultPage = demService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile file, Dem dem) {
        if (file != null) {
            demService.importData(file, dem);
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(demService.deleteByDemId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody Dem dem) {
        return CommonResult.success(demService.addDem(dem));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody Dem dem) {
        return CommonResult.success(demService.updateDem(dem));
    }

    @GetMapping("test")
    public CommonResult<List<Dem>> getAllData() {
        return CommonResult.success(demService.findAll());
    }
}
