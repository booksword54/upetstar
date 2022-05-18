package com.superb.upetstar.upscommunity.controller;

import com.superb.upetstar.common.exception.UPetStarLoginException;
import com.superb.upetstar.common.pojo.entity.*;
import com.superb.upetstar.common.pojo.entity.CUser;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.common.pojo.vo.CUserLoginVO;
import com.superb.upetstar.common.pojo.vo.CUserRegisterVO;
import com.superb.upetstar.service.ICUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author hym
 * @description
 */
@Api(tags = "社区管理员相关")
@RestController
@RequestMapping("/community/cUser")
public class CUserController {

    @Resource
    private ICUserService cuserService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public Result list() {
        return Result.success().data("cuserList", cuserService.list());
    }

    @ApiOperation("分页查询社区管理员列表")
    @GetMapping("/listPage/{limit}/{current}")
    public Result listPage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
        List<CUser> cUserPage = cuserService.listPage(current, limit);
        return Result.success().data("cUserPage", cUserPage);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody CUser cuser) {
        boolean save = cuserService.save(cuser);
        return save ? Result.success() : Result.fail();
    }

    /**
     * 信息
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable("id") Integer id) {
        CUser cuser = cuserService.getById(id);
        return Result.success().data("cuser", cuser);
    }

    @ApiOperation("查询社区所有成员")
    @GetMapping("/getUserByCId/{cId}")
    public Result getUserByCId(@PathVariable("cId") Integer cId) {
        List<User> userList = cuserService.getUserByCId(cId);
        return Result.success().data("userList", userList);
    }


    @ApiOperation("查询社区所有宠物")
    @GetMapping("/getPetByCId/{cId}")
    public Result getPetByCId(@PathVariable("cId") Integer cId) {
        List<Pet> petList = cuserService.getPetByCId(cId);
        return Result.success().data("petList", petList);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public Result update(@RequestBody CUser cuser) {
        boolean b = cuserService.updateById(cuser);
        return b ? Result.success() : Result.fail();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        boolean removeById = cuserService.removeById(id);
        return removeById ? Result.success() : Result.fail();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Integer[] ids) {
        boolean removeByIds = cuserService.removeByIds(Arrays.asList(ids));
        return removeByIds ? Result.success() : Result.fail();
    }


    @ApiOperation("社区管理员注册")
    @PostMapping("/register")
    public Result register(@RequestBody CUserRegisterVO registerVO) {
        int register = cuserService.register(registerVO);
        return register > 0 ? Result.success().message("注册成功") : Result.fail().message("注册失败");
    }

    @ApiOperation("社区管理员登录")
    @PostMapping("/login")
    public Result login(@RequestBody CUserLoginVO cUserLoginVO, HttpSession session) {
        CUser login = cuserService.login(cUserLoginVO);
        if (login == null) {
            throw new UPetStarLoginException();
        }
        session.setAttribute("cUser", login);
        return Result.success().message("登录成功").data("cUser", login);
    }

    @ApiOperation("管理员登出")
    @GetMapping("/logout")
    public Result logout(HttpSession session) {
        session.invalidate();
        return Result.success().message("登出成功");
    }

    @ApiOperation("根据社区id查询社区活动列表")
    @GetMapping("/getCActById/{cid}")
    public Result getCActById(@PathVariable("cid") Integer cid) {
        if (cid == null) {
            return Result.fail().message("社区id不能为空");
        }
        List<CActivity> cActivities = cuserService.getCActById(cid);
        return Result.success().data("cActivities", cActivities);
    }
}
