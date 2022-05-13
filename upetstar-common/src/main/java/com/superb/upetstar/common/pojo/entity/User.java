package com.superb.upetstar.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author hym
 * @description
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户OpenID")
    private String openId;
    private String username;
    private String gender;
    private Integer age;
    private String career;
    private String phone;
    //private Integer avatarId; // 头像id
    private String avatarUrl; // 头像路径
    private Integer communityId; // 社区id
    private String communityAddr; // 社区地址
    private String description; // 个人简介

}
