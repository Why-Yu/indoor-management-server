package com.whyyu.indoormanagementserver.controller;

import com.whyyu.indoormanagementserver.service.*;
import com.whyyu.indoormanagementserver.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author WhyYu
 * @Description 给前端的仪表盘提供数据
 * @Date 2021/8/2 20:34
 */
@RestController
@RequestMapping("/DashBoard")
public class DashBoardController {
    @Autowired
    WiFiService wiFiService;
    @Autowired
    AccessPointService accessPointService;
    @Autowired
    BlueToothService blueToothService;
    @Autowired
    ShapeModelService shapeModelService;
    @Autowired
    TilesService tilesService;
    @Autowired
    TdTilesService tdTilesService;

    @GetMapping("/data")
    public CommonResult<HashMap<String, Long>> getAllTableCount() {
        HashMap<String, Long> resultMap = new HashMap<>();
        resultMap.put("WiFi", wiFiService.count());
        resultMap.put("AP", accessPointService.count());
        resultMap.put("BlueTooth", blueToothService.count());
        resultMap.put("IndoorTopo", shapeModelService.count());
        resultMap.put("Tiles", tilesService.count());
        resultMap.put("3dTiles", tdTilesService.count());
        return CommonResult.success(resultMap);
    }
}
