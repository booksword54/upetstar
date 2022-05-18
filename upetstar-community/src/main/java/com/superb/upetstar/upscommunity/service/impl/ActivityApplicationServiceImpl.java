package com.superb.upetstar.upscommunity.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superb.upetstar.upscommunity.mapper.ActivityApplicationMapper;
import com.superb.upetstar.common.pojo.entity.ActivityApplication;
import com.superb.upetstar.service.IActivityApplicationService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @author hym
 * @description
 */
@DubboService
public class ActivityApplicationServiceImpl extends ServiceImpl<ActivityApplicationMapper, ActivityApplication> implements IActivityApplicationService {

    @Override
    public List<ActivityApplication> listPage(Integer current, Integer limit) {
        Page<ActivityApplication> page = new Page<>(current, limit);
        Page<ActivityApplication> selectPage = baseMapper.selectPage(page, null);
        return selectPage.getRecords();
    }

}
