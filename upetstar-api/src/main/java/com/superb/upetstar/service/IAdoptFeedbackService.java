package com.superb.upetstar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superb.upetstar.common.pojo.entity.AdoptFeedback;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
public interface IAdoptFeedbackService extends IService<AdoptFeedback> {

    /**
     * 分页查询领养反馈列表
     * @param current 当前页
     * @param limit 每页个数
     * @return 领养反馈分页
     */
    List<AdoptFeedback> listPage(Integer current, Integer limit);
}
