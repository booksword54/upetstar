package com.superb.upetstar.controller;


import com.superb.upetstar.common.pojo.entity.Image;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.service.IImageService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
@Api(tags = "图片相关")
@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    private IImageService imageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public Result list() {
        return Result.success().data("imageList", imageService.list());
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody Image image) {
        boolean save = imageService.save(image);
        return save ? Result.success() : Result.fail();
    }

    /**
     * 信息
     */
    @GetMapping("get/{id}")
    public Result get(@PathVariable("id") Integer id) {
        Image image = imageService.getById(id);
        return Result.success().data("image", image);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Image image) {
        boolean b = imageService.updateById(image);
        return b ? Result.success() : Result.fail();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        boolean removeById = imageService.removeById(id);
        return removeById ? Result.success() : Result.fail();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Integer[] ids) {
        boolean removeByIds = imageService.removeByIds(Arrays.asList(ids));
        return removeByIds ? Result.success() : Result.fail();
    }

}
