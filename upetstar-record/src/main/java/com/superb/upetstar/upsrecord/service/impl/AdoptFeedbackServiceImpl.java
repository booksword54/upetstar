package com.superb.upetstar.upsrecord.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superb.upetstar.upsrecord.mapper.AdoptFeedbackMapper;
import com.superb.upetstar.common.pojo.entity.AdoptFeedback;
import com.superb.upetstar.service.IAdoptFeedbackService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
@DubboService
public class AdoptFeedbackServiceImpl extends ServiceImpl<AdoptFeedbackMapper, AdoptFeedback> implements IAdoptFeedbackService {

    @Override
    public List<AdoptFeedback> listPage(Integer current, Integer limit) {
        Page<AdoptFeedback> page = new Page<>(current, limit);
        Page<AdoptFeedback> selectPage = baseMapper.selectPage(page, null);
        return selectPage.getRecords();
    }
}
