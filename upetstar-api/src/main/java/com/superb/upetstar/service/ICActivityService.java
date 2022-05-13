package com.superb.upetstar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superb.upetstar.common.pojo.entity.CActivity;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
public interface ICActivityService extends IService<CActivity> {

    /**
     * 分页查询社区活动列表
     *
     * @param current 当前页码
     * @param limit   每页个数
     * @return 社区活动分页
     */
    List<CActivity> listPage(Integer current, Integer limit);

    /**
     * 获取本社区的活动列表
     *
     * @return
     */
    List<CActivity> getActivityList(HttpSession session);

    /**
     * 根据社区id查询列表
     *
     * @param cid
     * @return
     */
    List<CActivity> getListByCId(Integer cid);

}
