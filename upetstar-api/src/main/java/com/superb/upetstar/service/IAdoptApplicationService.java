package com.superb.upetstar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superb.upetstar.common.pojo.entity.AdoptApplication;
import com.superb.upetstar.common.pojo.vo.AdoptApplicationVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
public interface IAdoptApplicationService extends IService<AdoptApplication> {

    /**
     * 分页查询领养申请列表
     *
     * @param current 当前页
     * @param limit   每页个数
     * @return 领养申请分页
     */
    List<AdoptApplication> listPage(Integer current, Integer limit);

    /**
     * 宠物申请
     *
     * @param adoptApplicationVO 表单参数VO
     */
    Integer apply(AdoptApplicationVO adoptApplicationVO);


    /**
     * 根据寄养人id查询宠物领养申请列表
     *
     * @param gid 寄养人id
     * @return
     */
    List<AdoptApplication> getListByGId(Integer gid);

    /**
     * 同意领养
     *
     * @param isAgreed     是否同意 1 不同意 2 同意
     * @param adoptApplyId 领养记录id
     * @return
     */
    boolean agree(Integer isAgreed, Integer adoptApplyId);
}
