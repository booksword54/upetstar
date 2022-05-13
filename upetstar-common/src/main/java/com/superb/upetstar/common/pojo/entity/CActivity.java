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
@TableName("c_activity")
public class CActivity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer communityId;
    private String name;
    private String description;
}
