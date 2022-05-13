package com.superb.upetstar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superb.upetstar.common.pojo.entity.SearchWord;
import com.superb.upetstar.common.pojo.vo.AdoptRecordVO;

import java.util.List;
import java.util.Set;

/**
 * @author hym
 * @description
 */
public interface ISearchWordService extends IService<SearchWord> {

    /**
     * 关键字搜索
     *
     * @param word 关键字 宠物名、类别、送养标题、送养描述
     */
    List<AdoptRecordVO> search(String word);

    /**
     * 查询热门搜索词
     *
     * @param start 从
     * @param end   到
     */
    Set<String> listHotWord(Integer start, Integer end);

    /**
     * 查询热搜记录列表
     */
    Set<AdoptRecordVO> listHotSearch();

    /**
     * 清空搜索缓存
     *
     * @param keyName 缓存key
     */
    void deleteSearchCache(String keyName);

}
