package com.superb.upetstar.upscommunity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superb.upetstar.common.exception.UPetStarException;
import com.superb.upetstar.upscommunity.mapper.CommunityMapper;
import com.superb.upetstar.common.pojo.entity.Community;
import com.superb.upetstar.service.ICommunityService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
@DubboService
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements ICommunityService {

    @Override
    public List<Community> listPage(Integer current, Integer limit) {
        Page<Community> page = new Page<>(current, limit);
        Page<Community> selectPage = baseMapper.selectPage(page, null);
        return selectPage.getRecords();
    }

    @Override
    public Community getByNameAndAddress(String name, String address) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(address)) {
            throw new UPetStarException("社区名或社区地址不能为空");
        }
        QueryWrapper<Community> communityQueryWrapper = new QueryWrapper<>();
        communityQueryWrapper.eq("name", name).eq("address", address);
        return baseMapper.selectOne(communityQueryWrapper);
    }

}
