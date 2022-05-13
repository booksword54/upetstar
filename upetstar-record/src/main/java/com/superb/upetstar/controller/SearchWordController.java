package com.superb.upetstar.controller;

import com.superb.upetstar.common.pojo.response.Result;
import com.superb.upetstar.common.pojo.vo.AdoptRecordVO;
import com.superb.upetstar.service.ISearchWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author hym
 * @description
 */
@Api(tags = "搜索关键词相关")
@RestController
public class SearchWordController {

    @Resource
    private ISearchWordService searchWordService;

    @ApiOperation("显示热门搜索词")
    @GetMapping("hotWords")
    public Result hotWords() {
        Set<String> hotWords = searchWordService.listHotWord(0, 10);
        return Result.success().data("hotWords", hotWords);
    }

    @ApiOperation("显示热门搜索记录")
    @GetMapping("/hotList")
    public Result hotList() {
        Set<AdoptRecordVO> hotList = searchWordService.listHotSearch();
        return Result.success().data("hotList", hotList);
    }

    @ApiOperation("清空搜索缓存")
    @DeleteMapping("/deleteSearchCache")
    public Result deleteSearchCache(@RequestParam("keyName") String keyName) {
        searchWordService.deleteSearchCache(keyName);
        return Result.success();
    }
}
