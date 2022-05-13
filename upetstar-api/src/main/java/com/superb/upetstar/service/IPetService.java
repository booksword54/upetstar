package com.superb.upetstar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superb.upetstar.common.pojo.entity.AdoptApplication;
import com.superb.upetstar.common.pojo.entity.Pet;
import com.superb.upetstar.common.pojo.es.ESPet;
import com.superb.upetstar.common.pojo.vo.PetDetailVO;
import com.superb.upetstar.common.pojo.vo.PetListVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
public interface IPetService extends IService<Pet> {

    /**
     * 按照宠物主人id查询宠物列表
     *
     * @param ownerId 宠物主人id
     * @return 宠物列表
     */
    List<Pet> findByOwnerId(Integer ownerId);

    /**
     * 按照宠物主人id查询宠物列表视图列表
     *
     * @param ownerId 宠物主人id
     * @return 宠物列表视图列表
     */
    List<PetListVO> findPetListVOsByOwnerId(Integer ownerId);

    /**
     * 分页查询宠物列表
     *
     * @param current 当前页码
     * @param limit   每页个数
     * @return 宠物列表分页
     */
    List<Pet> listPage(Integer current, Integer limit);

    /**
     * 根据领养申请更新宠物信息
     *
     * @param adoptApplication
     * @return
     */
    int updateByAdoptApply(AdoptApplication adoptApplication);

    /**
     * 根据宠物id查询宠物详情
     *
     * @param id
     * @return
     */
    PetDetailVO findDetailById(Integer id);

    /**
     * 实体对象转换为索引对象
     *
     * @param pet
     * @return
     */
    ESPet buildESPet(Pet pet);

    /**
     * 根据社区id获取宠物列表
     *
     * @param cId
     * @return
     */
    List<Pet> getPetByCId(Integer cId);
}
