package com.superb.upetstar.controller;


import com.superb.upetstar.common.pojo.entity.AdoptFeedback;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.service.IAdoptFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
@Api(tags = "领养反馈相关")
@RestController
@RequestMapping("/adoptFeedback")
public class AdoptFeedbackController {

    @Resource
    private IAdoptFeedbackService adoptFeedbackService;

    /**
     * 列表
     */
    @ApiOperation("查询所有领养反馈")
    @GetMapping("/list")
    public Result list() {
        return Result.success().data("adoptFeedbackList", adoptFeedbackService.list());
    }

    @ApiOperation("分页查询领养反馈列表")
    @GetMapping("/listPage/{limit}/{current}")
    public Result listPage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
        List<AdoptFeedback> adoptFeedbackPage = adoptFeedbackService.listPage(current, limit);
        return Result.success().data("adoptFeedbackPage", adoptFeedbackPage);
    }

    /**
     * 保存
     */
    @ApiOperation("新增领养反馈")
    @PostMapping("/save")
    public Result save(@RequestBody AdoptFeedback adoptFeedback) {
        boolean save = adoptFeedbackService.save(adoptFeedback);
        return save ? Result.success() : Result.fail();
    }

    /**
     * 信息
     */
    @ApiOperation("根据id查询领养反馈")
    @GetMapping("get/{id}")
    public Result get(@ApiParam("领养反馈id") @PathVariable("id") Integer id) {
        AdoptFeedback adoptFeedback = adoptFeedbackService.getById(id);
        return Result.success().data("adoptFeedback", adoptFeedback);
    }

    /**
     * 修改
     */
    @ApiOperation("更新领养反馈")
    @PutMapping("/update")
    public Result update(@ApiParam("上传领养反馈") @RequestBody AdoptFeedback adoptFeedback) {
        boolean b = adoptFeedbackService.updateById(adoptFeedback);
        return b ? Result.success() : Result.fail();
    }

    /**
     * 删除
     */
    @ApiOperation("根据id删除指定领养反馈")
    @DeleteMapping("/delete/{id}")
    public Result delete(@ApiParam("删除领养反馈id") @PathVariable("id") Integer id) {
        boolean removeById = adoptFeedbackService.removeById(id);
        return removeById ? Result.success() : Result.fail();
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除领养反馈")
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@ApiParam("批量删除id") @RequestBody Integer[] ids) {
        boolean removeByIds = adoptFeedbackService.removeByIds(Arrays.asList(ids));
        return removeByIds ? Result.success() : Result.fail();
    }

}
