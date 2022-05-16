package com.superb.upetstar.common.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hym
 * @description
 */
@ApiModel("社区管理员登录VO")
@Data
public class CUserLoginVO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("管理账号")
    private String cAccount;
    @ApiModelProperty("登陆密码")
    private String cPassword;
}
