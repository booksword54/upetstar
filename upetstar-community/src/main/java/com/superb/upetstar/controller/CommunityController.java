package com.superb.upetstar.controller;


import com.superb.upetstar.common.pojo.entity.Community;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.service.ICommunityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
@Api(tags = "社区相关")
@RestController
@RequestMapping("/community")
public class CommunityController {

    @Resource
    private ICommunityService communityService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public Result list() {
        return Result.success().data("communityList", communityService.list());
    }

    @ApiOperation("分页查询社区列表")
    @GetMapping("/listPage/{limit}/{current}")
    public Result listPage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
        List<Community> communityPage = communityService.listPage(current, limit);
        return Result.success().data("communityPage", communityPage);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody Community community) {
        boolean save = communityService.save(community);
        return save ? Result.success() : Result.fail();
    }

    /**
     * 信息
     */
    @GetMapping("get/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        Community community = communityService.getById(id);
        return Result.success().data("community", community);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Community community) {
        boolean b = communityService.updateById(community);
        return b ? Result.success() : Result.fail();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        boolean removeById = communityService.removeById(id);
        return removeById ? Result.success() : Result.fail();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Integer[] ids) {
        boolean removeByIds = communityService.removeByIds(Arrays.asList(ids));
        return removeByIds ? Result.success() : Result.fail();
    }

}
