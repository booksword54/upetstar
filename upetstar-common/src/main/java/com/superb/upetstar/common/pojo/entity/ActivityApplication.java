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
@TableName("activity_application")
public class ActivityApplication extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String username;
    private String petname;
    private String phone;
    private String address;
}
