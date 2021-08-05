package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.ShapeModel;
import com.whyyu.indoormanagementserver.service.ShapeModelService;
import com.whyyu.indoormanagementserver.util.CommonResult;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/3 12:14
 */
@RestController
@RequestMapping("/IndoorTopo")
public class IndoorTopoController {
    @Autowired
    ShapeModelService shapeModelService;
    @Value("${file.targetPath}")
    private String targetPath;

    @PostMapping("/table/data")
    public CommonResult<Page<ShapeModel>> getPageData(@RequestBody PageParam pageParam) {
        Page<ShapeModel> resultPage = shapeModelService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile file, @RequestParam("buildId") String buildId,
                                           @RequestParam("floor") String floor) {
        if (file != null) {
            shapeModelService.importData(file, buildId, floor);
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(shapeModelService.deleteByShapeModelId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody ShapeModel shapeModel) {
        return CommonResult.success(shapeModelService.addShapeModel(shapeModel));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody ShapeModel shapeModel) {
        return CommonResult.success(shapeModelService.updateShapeModel(shapeModel));
    }

    @GetMapping("test")
    public CommonResult<List<ShapeModel>> getAllData() {
        return CommonResult.success(shapeModelService.findAll());
    }
}

