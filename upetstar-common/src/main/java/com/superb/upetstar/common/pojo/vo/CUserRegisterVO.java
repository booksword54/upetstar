package com.superb.upetstar.common.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hym
 * @description
 */
@Data
@ApiModel("社区管理员注册VO")
public class CUserRegisterVO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("社区名称")
    private String cName;
    @ApiModelProperty("详细地址")
    private String cAddr;
    @ApiModelProperty("管理账号")
    private String cAccount;
    @ApiModelProperty("登陆密码")
    private String cPassword;
    @ApiModelProperty("社区描述")
    private String description;
}
