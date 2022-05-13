package com.superb.upetstar.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("c_user")
public class CUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer cId;
    private String cAccount;
    private String cPassword;
    private String cName;
    private String cAddr;
    private String description;
}
