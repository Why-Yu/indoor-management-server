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
    @Autowired
    PlanService planService;
    @Autowired
    RemoteImageService remoteImageService;
    @Autowired
    PoiService poiService;
    @Autowired
    DemService demService;
    @Autowired
    PanoramaService panoramaService;
    @Autowired
    PointCloudService pointCloudService;

    @GetMapping("/data")
    public CommonResult<HashMap<String, Long>> getAllTableCount() {
        HashMap<String, Long> resultMap = new HashMap<>();
        resultMap.put("WiFi", wiFiService.count());
        resultMap.put("AP", accessPointService.count());
        resultMap.put("BlueTooth", blueToothService.count());
        resultMap.put("IndoorTopo", shapeModelService.count());
        resultMap.put("Tiles", tilesService.count());
        resultMap.put("3dTiles", tdTilesService.count());
        resultMap.put("Plan", planService.count());
        resultMap.put("RemoteImage", remoteImageService.count());
        resultMap.put("Poi", poiService.count());
        resultMap.put("Dem", demService.count());
        resultMap.put("Panorama", panoramaService.count());
        resultMap.put("PointCloud", pointCloudService.count());
        return CommonResult.success(resultMap);
    }
}
