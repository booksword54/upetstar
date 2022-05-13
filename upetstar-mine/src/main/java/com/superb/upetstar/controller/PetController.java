package com.superb.upetstar.controller;


import com.superb.upetstar.common.pojo.entity.Pet;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.common.pojo.vo.PetListVO;
import com.superb.upetstar.repository.PetRepository;
import com.superb.upetstar.service.IPetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.*;
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
@RestController
@Api(tags = "宠物相关")
@RequestMapping("/pet")
@CacheConfig(cacheNames = "petCache", keyGenerator = "keyGenerator", cacheManager = "cacheManager")
public class PetController {

    @Resource
    private IPetService petService;

    @Resource
    private PetRepository petRepository;

    /**
     * 列表
     */
    @Cacheable
    @GetMapping("/list")
    public Result list() {
        return Result.success().data("petDetailList", petService.list());
    }

    @Cacheable
    @ApiOperation("分页查询宠物列表")
    @GetMapping("/listPage/{limit}/{current}")
    public Result listPage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
        List<Pet> petPage = petService.listPage(current, limit);
        return Result.success().data("petPage", petPage);
    }

    /**
     * 保存
     */
    @ApiOperation("添加宠物")
    @Caching(
            put = @CachePut(condition = "#result.code==200"),
            evict = @CacheEvict(cacheNames = {"userCache", "petCache"}, beforeInvocation = true, allEntries = true)
    )
    @PostMapping("/save")
    public Result save(@RequestBody Pet pet) {
        petService.save(pet);
        petRepository.save(pet.buildESPet()); // 新增文档
        return Result.success();
    }

    /**
     * 信息
     */
    @ApiOperation("按id查询宠物")
    @Cacheable
    @GetMapping("get/{id}")
    public Result get(@PathVariable("id") Integer id) {
        Pet pet = petService.getById(id);
        return Result.success().data("pet", pet);
    }

    @ApiOperation("按照宠物主人id查询宠物")
    @Cacheable
    @GetMapping("/findByOwnerId")
    public Result findByOwnerId(@RequestParam Integer ownerId) {
        List<Pet> petList = petService.findByOwnerId(ownerId);
        return Result.success().data("petList", petList);
    }

    @ApiOperation("按照宠物主人id查询宠物id和宠物名")
    @Cacheable
    @GetMapping("/findPetListVOsByOwnerId")
    public Result findPetListVOsByOwnerId(@RequestParam Integer ownerId) {
        List<PetListVO> petListVOS = petService.findPetListVOsByOwnerId(ownerId);
        return Result.success().data("petListVOS", petListVOS);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Caching(
            put = @CachePut(condition = "#result.code==200"),
            evict = @CacheEvict(cacheNames = {"userCache", "petCache", "adoptCache"}, beforeInvocation = true, allEntries = true)
    )
    public Result update(@RequestBody Pet pet) {
        petService.updateById(pet);
        petRepository.save(pet.buildESPet()); // 更新文档
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    @CacheEvict(cacheNames = {"userCache", "petCache", "adoptCache"}, allEntries = true, beforeInvocation = true)
    public Result delete(@PathVariable("id") Integer id) {
        petService.removeById(id);
        petRepository.deleteById(id);// 删除文档
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/deleteBatch")
    @CacheEvict(cacheNames = {"userCache", "petCache", "adoptCache"}, allEntries = true, beforeInvocation = true)
    public Result deleteBatch(@RequestBody Integer[] ids) {
        petService.removeByIds(Arrays.asList(ids));
        for (Integer id : ids) {
            petRepository.deleteById(id); // 删除文档
        }
        return Result.success();
    }

}
