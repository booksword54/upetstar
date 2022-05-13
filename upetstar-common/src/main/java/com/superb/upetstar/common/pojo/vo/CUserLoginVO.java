package com.superb.upetstar.common.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hym
 * @description
 */
@ApiModel("社区管理员登录VO")
@Data
public class CUserLoginVO {
    @ApiModelProperty("管理账号")
    private String cAccount;
    @ApiModelProperty("登陆密码")
    private String cPassword;
}
