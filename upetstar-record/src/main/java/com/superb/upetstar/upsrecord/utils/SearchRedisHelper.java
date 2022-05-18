package com.superb.upetstar.upsrecord.utils;

import com.superb.upetstar.common.pojo.vo.AdoptRecordVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author hym
 * @description redis搜索辅助类
 */
@Component
public class SearchRedisHelper {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 热搜词key
     */
    public static final String HOT_WORD = "adopt_hot_word";

    /**
     * 热搜记录key
     */
    public static final String HOT_SEARCH = "adopt_hot_search";

    /**
     * 热搜key的过期时间 1 DAYS
     */
    public static final Integer HOT_SEARCH_EXPIRE_TIME = 1;


    /**
     * redis过期时间 1天
     * expire是懒加载，不设置key的时候是不会执行的
     */
    @PostConstruct
    public void setHotSearchExpireTime() {
        redisTemplate.expire(HOT_WORD, HOT_SEARCH_EXPIRE_TIME, TimeUnit.DAYS);
        redisTemplate.expire(HOT_SEARCH, HOT_SEARCH_EXPIRE_TIME, TimeUnit.DAYS);
    }

    /**
     * 使用Sorted Set记录keyword
     * zincrby命令，对于一个Sorted Set，存在的就把分数加1D(可自行设定)，不存在就创建一个分数为1的成员
     *
     * @param keyword 搜索关键词
     */
    public void searchZincrby(String keyword) {
        // 名为adopt_hot_word的Sorted Set不用预先创建，不存在会自动创建，存在则向里添加数据
        redisTemplate.opsForZSet().incrementScore(HOT_WORD, keyword, 1D);
    }

    /**
     * zrevrange命令, 查询Sorted Set中指定范围的值
     * 返回的有序集合中，score大的在前面
     * zrevrange方法无需担心用于指定范围的start和end出现越界报错问题。
     *
     * @param start 查询范围开始位置
     * @param end   查询范围结束位置
     */
    public Set<ZSetOperations.TypedTuple<String>> queryTopSearchHotWord(Integer start, Integer end) {
        return (Set<ZSetOperations.TypedTuple<String>>) redisTemplate.opsForZSet().reverseRangeWithScores(HOT_WORD, start, end);
    }

    /**
     * 删除指定的key
     *
     * @param keyName keyName
     */
    public void deleteKey(String keyName) {
        redisTemplate.delete(keyName);
    }

    /**
     * 热搜列表
     */
    public Set<AdoptRecordVO> listHotSearch() {
        // 按分数倒序
        return redisTemplate.opsForZSet().reverseRangeWithScores(HOT_SEARCH, 0, 10);
    }

    /**
     * redis添加热搜
     *
     * @param adoptRecordVOS 搜索记录
     */
    public void addRedisHotSearch(List<AdoptRecordVO> adoptRecordVOS) {
        // 分数 + 1
        adoptRecordVOS.forEach(adoptRecordVO -> redisTemplate.opsForZSet().incrementScore(HOT_SEARCH, adoptRecordVO, 1D));
    }
}
