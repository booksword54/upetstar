package com.superb.upetstar.upsrecord.controller;

import com.superb.upetstar.common.pojo.entity.AdoptRecord;
import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.common.pojo.vo.AdoptRecordDetailVO;
import com.superb.upetstar.common.pojo.vo.AdoptRecordVO;
import com.superb.upetstar.upsrecord.repository.AdoptRecordRepository;
import com.superb.upetstar.service.IAdoptRecordService;
import com.superb.upetstar.service.ISearchWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "领养记录相关")
@RequestMapping("/record/adoptRecord")
@CacheConfig(cacheNames = "adoptCache", keyGenerator = "keyGenerator", cacheManager = "cacheManager")
public class AdoptRecordController {

    @Resource
    private IAdoptRecordService adoptRecordService;

    @Resource
    private AdoptRecordRepository adoptRecordRepository;

    @Resource
    private ISearchWordService searchWordService;

    /**
     * 列表
     */
    @ApiOperation("查询所有领养记录")
    @Cacheable
    @GetMapping("/list")
    public Result list() {
        return Result.success().data("adoptRecordList", adoptRecordService.list());
    }

    @ApiOperation("分页查询领养记录列表")
    @Cacheable
    @GetMapping("/listPage/{limit}/{current}")
    public Result listPage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
        List<AdoptRecord> adoptRecordPage = adoptRecordService.listPage(current, limit);
        return Result.success().data("adoptRecordPage", adoptRecordPage);
    }

    /**
     * 保存
     */
    @ApiOperation("新增领养记录")
    @CacheEvict(cacheNames = {"petCache", "adoptCache", "userCache"}, beforeInvocation = true, allEntries = true)
    @PostMapping("/save")
    public Result save(@RequestBody AdoptRecord adoptRecord) {
        adoptRecordService.save(adoptRecord);
        adoptRecordRepository.save(adoptRecord.buildESAdoptRecord());  // 新增文档
        return Result.success();
    }

    /**
     * 信息
     */
    @ApiOperation("根据id查询领养记录")
    @Cacheable
    @GetMapping("get/{id}")
    public Result get(@ApiParam("领养记录id") @PathVariable("id") Integer id) {
        AdoptRecord adoptRecord = adoptRecordService.getById(id);
        return Result.success().data("adoptRecord", adoptRecord);
    }

    @ApiOperation("根据领养记录id查询领养记录详情")
    @Cacheable
    @GetMapping("/getAdoptDetail/{id}")
    public Result getAdoptRecordDetail(@ApiParam("领养记录id") @PathVariable("id") Integer id) {
        AdoptRecordDetailVO adoptRecord = adoptRecordService.getAdoptRecordDetail(id);
        return Result.success().data("adoptRecord", adoptRecord);
    }

    @ApiOperation("根据送养记录id查询送养记录详情")
    @Cacheable
    @GetMapping("/getGiveDetail/{id}")
    public Result getGiveRecordDetail(@ApiParam("送养记录id") @PathVariable("id") Integer id) {
        AdoptRecordDetailVO adoptRecord = adoptRecordService.getGiveRecordDetail(id);
        return Result.success().data("adoptRecord", adoptRecord);
    }

    /**
     * 修改
     */
    @ApiOperation("更新领养记录")
    @CacheEvict(cacheNames = {"petCache", "adoptCache", "userCache"}, beforeInvocation = true, allEntries = true)
    @PutMapping("/update")
    public Result update(@ApiParam("上传领养记录") @RequestBody AdoptRecord adoptRecord) {
        adoptRecordService.updateById(adoptRecord);
        adoptRecordRepository.save(adoptRecord.buildESAdoptRecord()); // 更新文档
        return Result.success();
    }

    @ApiOperation("领养审核")
    @GetMapping("/audit/{id}")
    public Result audit(@PathVariable("id") Integer id, @RequestParam Integer auditId, @RequestParam Integer status) {
        Integer audit = adoptRecordService.audit(id, auditId, status);
        return audit > 0 ? Result.success() : Result.fail();
    }

    /**
     * 删除
     */
    @ApiOperation("根据id删除指定领养记录")
    @CacheEvict(cacheNames = {"petCache", "adoptCache", "userCache"}, beforeInvocation = true, allEntries = true)
    @DeleteMapping("/delete/{id}")
    public Result delete(@ApiParam("删除领养记录id") @PathVariable("id") Integer id) {
        adoptRecordService.removeById(id);
        adoptRecordRepository.deleteById(id);// 删除文档
        return Result.success();
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除领养记录")
    @CacheEvict(cacheNames = {"petCache", "adoptCache", "userCache"}, beforeInvocation = true, allEntries = true)
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@ApiParam("批量删除id") @RequestBody Integer[] ids) {
        adoptRecordService.removeByIds(Arrays.asList(ids));
        for (Integer id : ids) {
            adoptRecordRepository.deleteById(id); // 删除文档
        }
        return Result.success();
    }


    @ApiOperation("送养记录关键字搜索")
    @Cacheable
    @GetMapping("/search")
    public Result search(@RequestParam String word) {
        List<AdoptRecordVO> adoptRecordVOS = searchWordService.search(word);
        return Result.success().data("adoptRecordVOS", adoptRecordVOS);
    }

}
