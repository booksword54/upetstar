package com.superb.upetstar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superb.upetstar.common.pojo.entity.AdoptApplication;
import com.superb.upetstar.common.pojo.entity.Community;
import com.superb.upetstar.common.pojo.entity.User;
import com.superb.upetstar.common.pojo.vo.AdoptRecordVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
public interface IUserService extends IService<User> {

    /**
     * 根据社区id获取用户列表
     */
    List<User> getUserByCId(Integer cid);

    /**
     * 分页查询用户列表
     *
     * @param current 当前页码
     * @param limit   每页个数
     * @return 用户分页
     */
    List<User> listPage(Integer current, Integer limit);

    /**
     * 通过id获取领养记录
     *
     * @param adoptId 用户id
     * @return 领养记录列表
     */
    List<AdoptRecordVO> getAdoptRecordById(Integer adoptId);

    /**
     * 通过id获取送养记录
     *
     * @param giveId 用户id
     * @return 领养记录列表
     */
    List<AdoptRecordVO> getGiveRecordById(Integer giveId);

    /**
     * 根据openid查询用户
     *
     * @param openId
     * @return
     */
    User getByOpenId(String openId);

    /**
     * 查询宠物申请列表
     *
     * @param uid
     * @return
     */
    List<AdoptApplication> getApplyList(Integer uid);

    /**
     * 用户同意领养申请
     *
     * @param isAgreed     是否同意  1 不同意 2 同意
     * @param adoptApplyId 领养申请id
     * @return true 同意 false 拒绝
     */
    boolean agreeAdopt(Integer isAgreed, Integer adoptApplyId);

    /**
     * 根据领养人id查询社区信息
     *
     * @param userId
     * @return
     */
    Community getByUId(Integer userId);


    /**
     * 渲染用户信息
     */
    //UserInfoVO get();

    /**
     * 获取用户领养记录
     *
     * @return
     */
    //List<AdoptRecordVO> getAdoptRecord();

    /**
     * @return
     */
    //List<AdoptRecordVO> getGiveRecord();
}
