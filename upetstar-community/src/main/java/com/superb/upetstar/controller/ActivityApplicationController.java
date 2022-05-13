package com.superb.upetstar.controller;

import com.superb.upetstar.common.pojo.entity.ActivityApplication;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.service.IActivityApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author hym
 * @description
 */
@Api(tags = "社区活动报名相关")
@RestController
@RequestMapping("/activityApply")
public class ActivityApplicationController {

    @Resource
    private IActivityApplicationService activityApplicationService;

    /**
     * 列表
     */
    @ApiOperation("社区活动报名列表")
    @GetMapping("/list")
    public Result list() {
        return Result.success().data("activityApplicationList", activityApplicationService.list());
    }

    @ApiOperation("分页查询活动报名列表")
    @GetMapping("/listPage/{limit}/{current}")
    public Result listPage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
        List<ActivityApplication> activityApplicationPage = activityApplicationService.listPage(current, limit);
        return Result.success().data("activityApplicationPage", activityApplicationPage);
    }

    /**
     * 保存
     */
    @ApiOperation("保存社区活动报名记录")
    @PostMapping("/save")
    public Result save(@RequestBody ActivityApplication activityApplication) {
        boolean save = activityApplicationService.save(activityApplication);
        return save ? Result.success() : Result.fail();
    }

    /**
     * 信息
     */
    @ApiOperation("根据id查询社区活动报名记录")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        ActivityApplication activityApplication = activityApplicationService.getById(id);
        return Result.success().data("activityApplication", activityApplication);
    }

    /**
     * 修改
     */
    @ApiOperation("修改社区活动报名记录")
    @PutMapping("/update")
    public Result update(@RequestBody ActivityApplication activityApplication) {
        boolean b = activityApplicationService.updateById(activityApplication);
        return b ? Result.success() : Result.fail();
    }

    /**
     * 删除
     */
    @ApiOperation("删除社区活动报名记录")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        boolean removeById = activityApplicationService.removeById(id);
        return removeById ? Result.success() : Result.fail();
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除社区活动报名记录")
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody String[] ids) {
        boolean removeByIds = activityApplicationService.removeByIds(Arrays.asList(ids));
        return removeByIds ? Result.success() : Result.fail();
    }

}
