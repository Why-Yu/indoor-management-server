package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.RemoteImage;
import com.whyyu.indoormanagementserver.service.RemoteImageService;
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
 * @Date 2021/8/6 22:26
 */
@RestController
@RequestMapping("/RemoteImage")
public class RemoteImageController {
    @Autowired
    RemoteImageService remoteImageService;

    @PostMapping("/table/data")
    public CommonResult<Page<RemoteImage>> getPageData(@RequestBody PageParam pageParam) {
        Page<RemoteImage> resultPage = remoteImageService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            remoteImageService.importData(file);
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }
    @GetMapping(value = "/{index}")
    public void getImg(@PathVariable Integer index, HttpServletResponse response) {
        RemoteImage remoteImage = remoteImageService.findByIndex(index);
        String extension = "png";
        String path = remoteImageService.remoteImagePathBuild(remoteImage.getName(), extension);
        remoteImageService.getImage(response, path, extension);
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(remoteImageService.deleteByRemoteImageId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody RemoteImage remoteImage) {
        return CommonResult.success(remoteImageService.addRemoteImage(remoteImage));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody RemoteImage remoteImage) {
        return CommonResult.success(remoteImageService.updateRemoteImage(remoteImage));
    }

    @GetMapping("test")
    public CommonResult<List<RemoteImage>> getAllData() {
        return CommonResult.success(remoteImageService.findAll());
    }
}
