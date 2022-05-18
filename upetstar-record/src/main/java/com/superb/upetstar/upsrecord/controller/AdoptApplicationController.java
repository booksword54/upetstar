package com.superb.upetstar.upsrecord.controller;


import com.superb.upetstar.common.pojo.entity.AdoptApplication;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.common.pojo.vo.AdoptApplicationVO;
import com.superb.upetstar.service.IAdoptApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "领养申请相关")
@RestController
@RequestMapping("/record/adoptApplication")
public class AdoptApplicationController {

    @Resource
    private IAdoptApplicationService adoptApplicationService;

    /**
     * 列表
     */
    @ApiOperation("查询所有领养申请")
    @GetMapping("/list")
    public Result list() {
        return Result.success().data("adoptApplicationList", adoptApplicationService.list());
    }

    @ApiOperation("分页查询领养申请列表")
    @GetMapping("/listPage/{limit}/{current}")
    public Result listPage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
        List<AdoptApplication> adoptApplicationPage = adoptApplicationService.listPage(current, limit);
        return Result.success().data("adoptApplicationPage", adoptApplicationPage);
    }

    /**
     * 保存
     */
    @ApiOperation("新增领养申请")
    @PostMapping("/save")
    public Result save(@RequestBody AdoptApplication adoptApplication) {
        boolean save = adoptApplicationService.save(adoptApplication);
        return save ? Result.success() : Result.fail();
    }

    /**
     * 信息
     */
    @ApiOperation("根据id查询领养申请")
    @GetMapping("get/{id}")
    public Result get(@ApiParam("领养申请id") @PathVariable("id") Integer id) {
        AdoptApplication adoptApplication = adoptApplicationService.getById(id);
        return Result.success().data("adoptApplication", adoptApplication);
    }

    /**
     * 修改
     */
    @ApiOperation("更新领养申请")
    @PutMapping("/update")
    public Result update(@ApiParam("上传领养申请") @RequestBody AdoptApplication adoptApplication) {
        boolean b = adoptApplicationService.updateById(adoptApplication);
        return b ? Result.success() : Result.fail();
    }

    /**
     * 删除
     */
    @ApiOperation("根据id删除指定领养申请")
    @DeleteMapping("/delete/{id}")
    public Result delete(@ApiParam("删除领养申请id") @PathVariable("id") Integer id) {
        boolean removeById = adoptApplicationService.removeById(id);
        return removeById ? Result.success() : Result.fail();
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除领养申请")
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@ApiParam("批量删除id") @RequestBody Integer[] ids) {
        boolean removeByIds = adoptApplicationService.removeByIds(Arrays.asList(ids));
        return removeByIds ? Result.success() : Result.fail();
    }

    @ApiOperation("宠物领养申请")
    @PostMapping("/apply")
    public Result apply(@RequestBody AdoptApplicationVO adoptApplicationVO) {
        Integer apply = adoptApplicationService.apply(adoptApplicationVO);
        return apply > 0 ? Result.success() : Result.fail();
    }

}
