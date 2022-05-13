package com.superb.upetstar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superb.upetstar.common.pojo.entity.CActivity;
import com.superb.upetstar.common.pojo.entity.CUser;
import com.superb.upetstar.common.pojo.entity.Pet;
import com.superb.upetstar.common.pojo.entity.User;
import com.superb.upetstar.common.pojo.vo.CUserLoginVO;
import com.superb.upetstar.common.pojo.vo.CUserRegisterVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
public interface ICUserService extends IService<CUser> {

    /**
     * 查询社区所有成员
     *
     * @param cId
     * @return
     */
    List<User> getUserByCId(Integer cId);

    /**
     * 查询社区所有宠物
     *
     * @param cId
     * @return
     */
    List<Pet> getPetByCId(Integer cId);

    /**
     * 分页查询社区管理员列表
     *
     * @param current 当前页码
     * @param limit   每页个数
     * @return 社区管理员分页
     */
    List<CUser> listPage(Integer current, Integer limit);


    /**
     * 社区管理员注册
     *
     * @return
     */
    int register(CUserRegisterVO registerVO);

    /**
     * 社区管理员登录
     *
     * @param loginVO
     * @return
     */
    CUser login(CUserLoginVO loginVO);

    /**
     * 根据社区id查询社区活动列表
     *
     * @param id
     * @return
     */
    List<CActivity> getCActById(Integer id);
}
