package com.superb.upetstar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superb.upetstar.common.pojo.entity.Community;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
public interface ICommunityService extends IService<Community> {

    /**
     * 分页查询社区列表
     *
     * @param current 当前页码
     * @param limit   每页个数
     * @return 社区分页
     */
    List<Community> listPage(Integer current, Integer limit);

    /**
     * 根据唯一的社区名和社区地址获取社区
     *
     * @param name    社区名
     * @param address 社区地址
     * @return 社区信息
     */
    Community getByNameAndAddress(String name, String address);

}
