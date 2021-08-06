package com.whyyu.indoormanagementserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.whyyu.indoormanagementserver.entity.Plan;
import com.whyyu.indoormanagementserver.service.PlanService;
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
 * @Date 2021/8/6 17:51
 */
@RestController
@RequestMapping("/Plan")
public class PlanController {
    @Autowired
    PlanService planService;

    @PostMapping("/table/data")
    public CommonResult<Page<Plan>> getPageData(@RequestBody PageParam pageParam) {
        Page<Plan> resultPage = planService.findAllByPage(pageParam);
        return CommonResult.success(resultPage);
    }

    @PostMapping("/importData")
    public CommonResult<String> importData(@RequestParam("file") MultipartFile file, @RequestParam String name,
                                           @RequestParam Double longitude, @RequestParam Double latitude) {
        if (file != null) {
            planService.importData(file, name, longitude, latitude);
            return CommonResult.success("import success");
        } else {
            return CommonResult.failed("need to select one file at least");
        }
    }

    @GetMapping(value = "/{index}")
    public void getImg(@PathVariable Integer index, HttpServletResponse response) {
        Plan plan = planService.findByIndex(index);
        String extension = plan.getPath().substring(plan.getPath().indexOf(".") + 1);
        planService.getImage(response, plan.getPath(), extension);
    }

    @PostMapping("/table/delete")
    public CommonResult<Integer> deleteData(@RequestBody JSONObject jsonParam) {
        return CommonResult.success(planService.deleteByPlanId(jsonParam.getInteger("index")));
    }

    @PostMapping("/table/create")
    public CommonResult<Integer> createData(@RequestBody Plan plan) {
        return CommonResult.success(planService.addPlan(plan));
    }

    @PostMapping("/table/update")
    public CommonResult<Integer> updateData(@RequestBody Plan plan) {
        return CommonResult.success(planService.updatePlan(plan));
    }

    @GetMapping("test")
    public CommonResult<List<Plan>> getAllData() {
        return CommonResult.success(planService.findAll());
    }
}
