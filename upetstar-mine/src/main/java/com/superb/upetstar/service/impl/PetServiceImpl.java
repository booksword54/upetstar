package com.superb.upetstar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superb.upetstar.common.pojo.entity.AdoptApplication;
import com.superb.upetstar.common.pojo.entity.AdoptRecord;
import com.superb.upetstar.common.pojo.entity.Community;
import com.superb.upetstar.common.pojo.entity.Pet;
import com.superb.upetstar.common.pojo.es.ESPet;
import com.superb.upetstar.common.pojo.vo.PetDetailVO;
import com.superb.upetstar.common.pojo.vo.PetListVO;
import com.superb.upetstar.mapper.PetMapper;
import com.superb.upetstar.service.IAdoptRecordService;
import com.superb.upetstar.service.IPetService;
import com.superb.upetstar.service.IUserService;
import org.apache.commons.lang3.StringUtils;
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
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements IPetService {

    @DubboReference
    IAdoptRecordService adoptRecordService;

    @Resource
    IUserService userService;

    @Override
    public List<Pet> findByOwnerId(Integer ownerId) {
        if (ownerId == null) {
            return new ArrayList<>();
        }
        QueryWrapper<Pet> petQueryWrapper = new QueryWrapper<>();
        petQueryWrapper.eq("user_id", ownerId);
        return baseMapper.selectList(petQueryWrapper);
    }

    @Override
    public List<PetListVO> findPetListVOsByOwnerId(Integer ownerId) {
        if (ownerId == null) {
            return new ArrayList<>();
        }
        List<Pet> petList = findByOwnerId(ownerId);
        return petList.stream()
                .map(pet -> {
                    Integer id = pet.getId();
                    String name = pet.getName();
                    return new PetListVO(name, id);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Pet> listPage(Integer current, Integer limit) {
        current = (current != null ? current : 0);
        limit = (limit != null ? limit : 10);
        Page<Pet> page = new Page<>(current, limit);
        Page<Pet> selectPage = baseMapper.selectPage(page, null);
        return selectPage.getRecords();
    }

    @Override
    public ESPet buildESPet(Pet pet) {
        if (pet == null) {
            return null;
        }
        ESPet esPet = new ESPet();
        BeanUtils.copyProperties(pet, esPet);
        if (!StringUtils.isEmpty(pet.getImages())) {
            esPet.setImage(StringUtils.split(pet.getImages(), ",")[0]);
        }
        return esPet;
    }

    @Override
    public List<Pet> getPetByCId(Integer cId) {
        if (cId == null) {
            return new ArrayList<>();
        }
        QueryWrapper<Pet> petQueryWrapper = new QueryWrapper<>();
        petQueryWrapper.eq("community_id", cId);
        return baseMapper.selectList(petQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public int updateByAdoptApply(AdoptApplication adoptApplication) {
        if (adoptApplication == null) {
            return 0;
        }
        // 根据送养记录id查询宠物
        AdoptRecord adoptRecord = adoptRecordService.getById(adoptApplication.getAdoptRecordId());
        if (adoptRecord == null) { // 证明申请的送养记录不存在
            return 0;
        }
        Pet pet = baseMapper.selectById(adoptRecord.getPetId());
        if (pet == null) { // 申请的宠物不存在
            return 0;
        }
        // 根据领养人id查询社区信息
        Community community = userService.getByUId(adoptApplication.getUserId());
        if (community != null) { // 社区信息为空
            pet.setAddress(community.getAddress()); // 宠物社区地址
            pet.setCommunityId(community.getId()); // 宠物社区id
        }
        pet.setUserId(adoptApplication.getUserId()); // 主人id
        return baseMapper.updateById(pet); // 更新宠物信息
    }

    @Override
    public PetDetailVO findDetailById(Integer id) {
        if (id == null) {
            return null;
        }
        PetDetailVO petDetailVO = new PetDetailVO();
        Pet pet = baseMapper.selectById(id);
        if (pet == null) { // 查无宠物
            return null;
        }
        BeanUtils.copyProperties(pet, petDetailVO);
        if (pet.getImages() != null) {
            String[] split = StringUtils.split(pet.getImages(), ",");
            petDetailVO.setImages(split);
            petDetailVO.setAvatarUrl(split[0]);
        }
        petDetailVO.setPublishTime(pet.getCreateTime());
        return petDetailVO;
    }

}
