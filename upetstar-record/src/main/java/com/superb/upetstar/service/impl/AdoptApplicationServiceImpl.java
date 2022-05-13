package com.superb.upetstar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superb.upetstar.common.exception.UPetStarException;
import com.superb.upetstar.mapper.AdoptApplicationMapper;
import com.superb.upetstar.common.pojo.entity.AdoptApplication;
import com.superb.upetstar.common.pojo.entity.AdoptRecord;
import com.superb.upetstar.common.pojo.vo.AdoptApplicationVO;
import com.superb.upetstar.service.IAdoptApplicationService;
import com.superb.upetstar.service.IAdoptRecordService;
import com.superb.upetstar.service.IPetService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
@DubboService
public class AdoptApplicationServiceImpl extends ServiceImpl<AdoptApplicationMapper, AdoptApplication> implements IAdoptApplicationService {

    @Resource
    IAdoptRecordService adoptRecordService;

    @DubboReference
    IPetService petService;

    @Override
    public List<AdoptApplication> listPage(Integer current, Integer limit) {
        current = (current != null ? current : 0);
        limit = (limit != null ? limit : 10);
        Page<AdoptApplication> page = new Page<>(current, limit);
        Page<AdoptApplication> selectPage = baseMapper.selectPage(page, null);
        return selectPage.getRecords();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public Integer apply(AdoptApplicationVO adoptApplicationVO) {
        if (adoptApplicationVO == null) { // 插入申请为空，返回0
            return 0;
        }
        AdoptApplication adoptApplication = new AdoptApplication();
        BeanUtils.copyProperties(adoptApplicationVO, adoptApplication);
        return baseMapper.insert(adoptApplication);
    }

    @Override
    public List<AdoptApplication> getListByGId(Integer gid) {
        if (gid == null) {
            return null;
        }
        QueryWrapper<AdoptRecord> adoptRecordQueryWrapper = new QueryWrapper<>();
        // 根据寄养人id查询所有寄养记录id
        adoptRecordQueryWrapper.eq("give_id", gid);
        List<Integer> adoptRecordIds =
                adoptRecordService.list(adoptRecordQueryWrapper)
                        .stream()
                        .map(AdoptRecord::getId)
                        .collect(Collectors.toList());
        ArrayList<AdoptApplication> adoptApplications = new ArrayList<>();
        // 根据领养记录id查询所有领养申请
        for (Integer adoptRecordId : adoptRecordIds) {
            QueryWrapper<AdoptApplication> adoptApplicationQueryWrapper = new QueryWrapper<>();
            adoptApplicationQueryWrapper.eq("adopt_record_id", adoptRecordId);
            List<AdoptApplication> list = baseMapper.selectList(adoptApplicationQueryWrapper);
            adoptApplications.addAll(list);
        }
        return adoptApplications;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public boolean agree(Integer isAgreed, Integer adoptApplyId) {
        if (isAgreed == null || adoptApplyId == null) {
            throw new UPetStarException("请选中送养记录并选择是否同意");
        }
        QueryWrapper<AdoptApplication> adoptApplicationQueryWrapper = new QueryWrapper<>();
        adoptApplicationQueryWrapper.eq("id", adoptApplyId);
        AdoptApplication adoptApplication = baseMapper.selectOne(adoptApplicationQueryWrapper);
        if (adoptApplication == null) {
            throw new UPetStarException("送养记录不存在");
        }
        adoptApplication.setAdoptStatus(isAgreed);
        baseMapper.updateById(adoptApplication); // 更新领养申请状态
        if (isAgreed == 2) { // 送养用户同意，修改宠物信息
            // 根据领养申请更新宠物信息
            petService.updateByAdoptApply(adoptApplication);
        } // 不同意只更新申请状态信息即可
        return true;
    }
}
