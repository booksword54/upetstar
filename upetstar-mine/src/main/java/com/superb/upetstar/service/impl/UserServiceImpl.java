package com.superb.upetstar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superb.upetstar.common.exception.UPetStarException;
import com.superb.upetstar.common.pojo.entity.*;
import com.superb.upetstar.common.pojo.vo.AdoptRecordVO;
import com.superb.upetstar.mapper.UserMapper;
import com.superb.upetstar.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IPetService petService;

    @DubboReference
    private IAdoptRecordService adoptRecordService;

    @DubboReference
    private IAdoptApplicationService adoptApplicationService;

    @DubboReference
    private ICommunityService communityService;

    /**
     * 根据openId获取用户
     *
     * @param openId
     * @return
     */
    @Override
    public User getByOpenId(String openId) {
        if (StringUtils.isEmpty(openId)) {
            return null;
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("open_id", openId);
        User user = baseMapper.selectOne(userQueryWrapper);
        if (user == null) { // 没有open_id，注册新用户
            user = new User();
            user.setOpenId(openId);
            baseMapper.insert(user);
        }
        return user;
    }

    /**
     * 根据id查询宠物领养申请列表
     *
     * @param uid
     * @return
     */
    @Override
    public List<AdoptApplication> getApplyList(Integer uid) {
        if (uid == null) {
            return null;
        }
        return adoptApplicationService.getListByGId(uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public boolean agreeAdopt(Integer isAgreed, Integer adoptApplyId) {
        if (isAgreed == null || adoptApplyId == null) {
            throw new UPetStarException("请选中领养申请id并选择是否同意");
        }
        return adoptApplicationService.agree(isAgreed, adoptApplyId);
    }

    @Override
    public Community getByUId(Integer userId) {
        if (userId == null) {
            return null;
        }
        User user = baseMapper.selectById(userId);
        return communityService.getById(user.getCommunityId());
    }

    @Override
    public List<User> getUserByCId(Integer cid) {
        if (cid == null) {
            return new ArrayList<>();
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("community_id", cid);
        return baseMapper.selectList(userQueryWrapper);
    }

    @Override
    public List<User> listPage(Integer current, Integer limit) {
        current = (current != null ? current : 0);
        limit = (limit != null ? limit : 10);
        Page<User> page = new Page<>(current, limit);
        Page<User> selectPage = baseMapper.selectPage(page, null);
        return selectPage.getRecords();
    }

    @Override
    public List<AdoptRecordVO> getAdoptRecordById(Integer adoptId) {
        ArrayList<AdoptRecordVO> adoptRecordVOS = new ArrayList<>();
        if (adoptId == null) {
            return adoptRecordVOS;
        }
        // 根据领养者id查询领养记录
        List<AdoptRecord> list = adoptRecordService.listByAdoptId(adoptId);
        for (AdoptRecord adoptRecord : list) {
            // 封装领养记录相关信息
            AdoptRecordVO adoptRecordVO = new AdoptRecordVO();
            adoptRecordVO.setAdoptRecordId(adoptRecord.getId());  // 领养记录id
            adoptRecordVO.setTitle(adoptRecord.getTitle()); // 领送养标题
            adoptRecordVO.setDescription(adoptRecord.getDescription());  // 送养描述
            adoptRecordVO.setGiveTime(adoptRecord.getGiveTime()); // 送养时间
            // 封装宠物相关信息
            Integer petId = adoptRecord.getPetId();
            if (petId == null) { // 宠物id为空，不封装宠物信息
                adoptRecordVOS.add(adoptRecordVO);
                continue;
            }
            Pet pet = petService.getById(petId);
            if (pet != null) {
                String address = pet.getAddress();
                String breed = pet.getBreed();
                String images = pet.getImages();
                String firstImg = StringUtils.split(images, ",")[0];
                adoptRecordVO.setAddress(address); // 宠物地址
                adoptRecordVO.setBreed(breed); // 宠物种类
                adoptRecordVO.setImage(firstImg); // 宠物的第一张图
            }
            adoptRecordVOS.add(adoptRecordVO);
        }
        return adoptRecordVOS;
    }

    @Override
    public List<AdoptRecordVO> getGiveRecordById(Integer giveId) {
        ArrayList<AdoptRecordVO> adoptRecordVOS = new ArrayList<>();
        if (giveId == null) {
            return adoptRecordVOS;
        }
        // 根据送养者id查询送养记录
        List<AdoptRecord> list = adoptRecordService.listByGiveId(giveId);
        for (AdoptRecord adoptRecord : list) {
            // 封装领养记录相关信息
            AdoptRecordVO adoptRecordVO = new AdoptRecordVO();
            adoptRecordVO.setAdoptRecordId(adoptRecord.getId());  // 领养记录id
            adoptRecordVO.setTitle(adoptRecord.getTitle()); // 领送养标题
            adoptRecordVO.setDescription(adoptRecord.getDescription());  // 送养描述
            adoptRecordVO.setGiveTime(adoptRecord.getGiveTime()); // 送养时间
            // 封装宠物相关信息
            Integer petId = adoptRecord.getPetId();
            if (petId == null) { // 宠物id为空，不封装宠物信息
                adoptRecordVOS.add(adoptRecordVO);
                continue;
            }
            Pet pet = petService.getById(petId);
            if (pet != null) {
                String address = pet.getAddress();
                String breed = pet.getBreed();
                String images = pet.getImages();
                String firstImg = StringUtils.split(images, ",")[0];
                adoptRecordVO.setAddress(address); // 宠物地址
                adoptRecordVO.setBreed(breed); // 宠物种类
                adoptRecordVO.setImage(firstImg); // 宠物的第一张图
            }
            adoptRecordVOS.add(adoptRecordVO);
        }
        return adoptRecordVOS;
    }

    //@Override
    //public UserInfoVO get() {
    //    String openId = UPetStarUtil.getOpenId();
    //    User user = getByOpenId(openId);
    //    UserInfoVO userInfoVO = new UserInfoVO();
    //    BeanUtils.copyProperties(user, userInfoVO);
    //    return userInfoVO;
    //}

    //@Override
    //public List<AdoptRecordVO> getAdoptRecord() {
    //    String openId = UPetStarUtil.getOpenId();
    //    User user = getByOpenId(openId);
    //    return getAdoptRecordById(user.getId());
    //}

    //@Override
    //public List<AdoptRecordVO> getGiveRecord() {
    //    String openId = UPetStarUtil.getOpenId();
    //    User user = getByOpenId(openId);
    //    return getGiveRecordById(user.getId());
    //}
}
