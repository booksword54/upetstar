package com.superb.upetstar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superb.upetstar.common.pojo.entity.Report;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
public interface IReportService extends IService<Report> {

    /**
     * 分页查询举报信息列表
     *
     * @param current 当前页码
     * @param limit   每页个数
     * @return 举报信息分页
     */
    List<Report> listPage(Integer current, Integer limit);
}
