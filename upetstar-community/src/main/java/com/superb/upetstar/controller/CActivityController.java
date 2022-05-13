package com.superb.upetstar.controller;

import com.superb.upetstar.common.pojo.entity.CActivity;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.service.ICActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author hym
 * @description
 */
@Api(tags = "社区活动相关")
@RestController
@RequestMapping("/cActivity")
public class CActivityController {

    @Resource
    private ICActivityService cActivityService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public Result list() {
        return Result.success().data("cActivityList", cActivityService.list());
    }

    @ApiOperation("获取本社区的活动列表")
    @GetMapping("/getActivityList")
    public Result getActivityList(HttpSession httpSession) {
        List<CActivity> activities = cActivityService.getActivityList(httpSession);
        return Result.success().data("activities", activities);
    }

    @ApiOperation("分页查询社区活动列表")
    @GetMapping("/listPage/{limit}/{current}")
    public Result listPage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
        List<CActivity> cActivityPage = cActivityService.listPage(current, limit);
        return Result.success().data("cActivityPage", cActivityPage);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody CActivity cActivity) {
        boolean save = cActivityService.save(cActivity);
        return save ? Result.success() : Result.fail();
    }

    /**
     * 信息
     */
    @GetMapping("get/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        CActivity cActivity = cActivityService.getById(id);
        return Result.success().data("cActivity", cActivity);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody CActivity cActivity) {
        boolean b = cActivityService.updateById(cActivity);
        return b ? Result.success() : Result.fail();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        boolean removeById = cActivityService.removeById(id);
        return removeById ? Result.success() : Result.fail();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Integer[] ids) {
        boolean removeByIds = cActivityService.removeByIds(Arrays.asList(ids));
        return removeByIds ? Result.success() : Result.fail();
    }
}
