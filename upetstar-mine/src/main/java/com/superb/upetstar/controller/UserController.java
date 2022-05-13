package com.superb.upetstar.controller;

import com.superb.upetstar.common.pojo.entity.AdoptApplication;
import com.superb.upetstar.common.pojo.entity.User;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.common.pojo.vo.AdoptRecordVO;
import com.superb.upetstar.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author hym
 * @description
 */
@RestController
@Api(tags = "用户相关")
@RequestMapping("/user")
@CacheConfig(cacheNames = "userCache", keyGenerator = "keyGenerator", cacheManager = "cacheManager")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 列表
     */
    @Cacheable
    @GetMapping("/list")
    public Result list() {
        return Result.success().data("userList", userService.list());
    }

    @ApiOperation("分页查询用户列表")
    @Cacheable
    @GetMapping("/listPage/{limit}/{current}")
    public Result listPage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
        List<User> userPage = userService.listPage(current, limit);
        return Result.success().data("userPage", userPage);
    }

    /**
     * 保存
     */
    @Caching(
            put = @CachePut(condition = "#result.code==200"),
            evict = @CacheEvict(beforeInvocation = true, allEntries = true)
    )
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        boolean save = userService.save(user);
        return save ? Result.success() : Result.fail();
    }

    /**
     * 根据id查询用户
     */
    @Cacheable
    @GetMapping("get/{id}")
    public Result get(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return Result.success().data("user", user);
    }

    /**
     * 渲染当前用户信息
     */
    //@GetMapping("get")
    //public Result get() {
    //    UserInfoVO userInfoVO = userService.get();
    //    return Result.success().data("userInfo", userInfoVO);
    //}

    /**
     * 修改
     */
    @Caching(
            put = @CachePut(condition = "#result.code==200"),
            evict = @CacheEvict(cacheNames = {"userCache", "petCache", "adoptCache"}, beforeInvocation = true, allEntries = true)
    )
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        boolean b = userService.updateById(user);
        return b ? Result.success() : Result.fail();
    }

    /**
     * 删除
     */
    @CacheEvict(cacheNames = {"userCache", "petCache", "adoptCache"}, allEntries = true, beforeInvocation = true)
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        boolean removeById = userService.removeById(id);
        return removeById ? Result.success() : Result.fail();
    }

    /**
     * 批量删除
     */
    @CacheEvict(cacheNames = {"userCache", "petCache", "adoptCache"}, allEntries = true, beforeInvocation = true)
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody String[] ids) {
        boolean removeByIds = userService.removeByIds(Arrays.asList(ids));
        return removeByIds ? Result.success() : Result.fail();
    }

    @Cacheable
    @ApiOperation("通过id获取领养记录")
    @GetMapping("/getAdoptRecordById")
    public Result getAdoptRecordById(@RequestParam("id") Integer adoptId) {
        List<AdoptRecordVO> adoptRecordVOS = userService.getAdoptRecordById(adoptId);
        return Result.success().data("adoptRecordVOS", adoptRecordVOS);
    }

    @Cacheable
    @ApiOperation("通过id获取送养记录")
    @GetMapping("/getGiveRecordById")
    public Result getGiveRecordById(@RequestParam("id") Integer giveId) {
        List<AdoptRecordVO> adoptRecordVOS = userService.getGiveRecordById(giveId);
        return Result.success().data("adoptRecordVOS", adoptRecordVOS);
    }

    /**
     * 根据用户openId查询用户信息
     * 没有查询到就根据openId注册
     */
    @Cacheable
    @ApiOperation("根据用户openId查询用户信息")
    @GetMapping("/getByOpenId")
    public Result getByOpenId(@RequestParam("openId") String openId) {
        User user = userService.getByOpenId(openId);
        return Result.success().data("user", user);
    }

    /**
     * 查询宠物申请列表
     */
    @Cacheable
    @ApiOperation("查询宠物申请列表")
    @GetMapping("/getApplyList")
    public Result getApplyList(@RequestParam Integer uid) {
        List<AdoptApplication> adoptApplications = userService.getApplyList(uid);
        return Result.success().data("adoptApplications", adoptApplications);
    }

    @ApiOperation("同意领养宠物")
    @GetMapping("/agreeAdopt")
    public Result agreeAdopt(@RequestParam Integer isAgreed, @RequestParam Integer adoptApplyId) {
        boolean agree = userService.agreeAdopt(isAgreed, adoptApplyId); // 返回用户是否同意 1 不同意 2 同意
        return Result.success().data("agree", agree);
    }

    //@ApiOperation("获取当前用户领养记录")
    //@GetMapping("/getAdoptRecord")
    //public Result getAdoptRecord() {
    //    List<AdoptRecordVO> adoptRecordVOS = userService.getAdoptRecord();
    //    return Result.success().data("adoptRecordVOS", adoptRecordVOS);
    //}

    //@ApiOperation("获取当前用户送养记录")
    //@GetMapping("/getGiveRecord")
    //public Result getGiveRecord() {
    //    List<AdoptRecordVO> adoptRecordVOS = userService.getGiveRecord();
    //    return Result.success().data("adoptRecordVOS", adoptRecordVOS);
    //}

}
