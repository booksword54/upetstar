package com.superb.upetstar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superb.upetstar.common.exception.UPetStarException;
import com.superb.upetstar.common.exception.UPetStarLoginException;
import com.superb.upetstar.mapper.CUserMapper;
import com.superb.upetstar.common.pojo.entity.*;
import com.superb.upetstar.common.pojo.entity.CUser;
import com.superb.upetstar.common.pojo.vo.CUserLoginVO;
import com.superb.upetstar.common.pojo.vo.CUserRegisterVO;
import com.superb.upetstar.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
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
public class CUserServiceImpl extends ServiceImpl<CUserMapper, CUser> implements ICUserService {

    @DubboReference
    private IUserService userService;

    @DubboReference
    private IPetService petService;

    @Resource
    private ICommunityService communityService;

    @Resource
    private ICActivityService cActivityService;

    @Override
    public List<User> getUserByCId(Integer cId) {
        if (cId == null) {
            return new ArrayList<>();
        }
        return userService.getUserByCId(cId);
    }

    @Override
    public List<Pet> getPetByCId(Integer cId) {
        if (cId == null) {
            return new ArrayList<>();
        }
        return petService.getPetByCId(cId);
    }

    @Override
    public List<CUser> listPage(Integer current, Integer limit) {
        Page<CUser> page = new Page<>(current, limit);
        Page<CUser> selectPage = baseMapper.selectPage(page, null);
        return selectPage.getRecords();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public int register(CUserRegisterVO registerVO) {
        if (registerVO == null) {
            throw new UPetStarException("注册信息不能为空，请重新填写");
        }
        String cName = registerVO.getCName();
        String cAddr = registerVO.getCAddr();
        Integer cid = null;
        if (StringUtils.isNoneEmpty(cName) && StringUtils.isNoneEmpty(cAddr)) {
            // 根据社区名 + 社区地址唯一确定管理员所在社区
            Community community = communityService.getByNameAndAddress(cName, cAddr);
            if (community == null) {
                throw new UPetStarException("社区不存在");
            }
            cid = community.getId();
        }
        // 注册社区管理员
        CUser cUser = new CUser();
        cUser.setCAccount(registerVO.getCAccount())
                .setCPassword(registerVO.getCPassword())
                .setCId(cid)
                .setCName(registerVO.getCName())
                .setCAddr(registerVO.getCAddr())
                .setDescription(registerVO.getDescription());
        return baseMapper.insert(cUser);
    }

    @Override
    public CUser login(CUserLoginVO loginVO) {
        String cAccount = loginVO.getCAccount();
        String cPassword = loginVO.getCPassword();
        if (StringUtils.isEmpty(cAccount) || StringUtils.isEmpty(cPassword)) {
            throw new UPetStarLoginException("请输入账户名和密码");
        }
        QueryWrapper<CUser> cUserQueryWrapper = new QueryWrapper<>();
        cUserQueryWrapper.eq("c_account", cAccount);
        CUser cUser = baseMapper.selectOne(cUserQueryWrapper);
        if (cUser == null) {
            throw new UPetStarLoginException("用户不存在，请注册");
        }
        if (!cPassword.equals(cUser.getCPassword())) {
            throw new UPetStarLoginException("密码错误，请重新输入");
        }
        return cUser;
    }

    @Override
    public List<CActivity> getCActById(Integer cid) {
        return cActivityService.getListByCId(cid);
    }


}
