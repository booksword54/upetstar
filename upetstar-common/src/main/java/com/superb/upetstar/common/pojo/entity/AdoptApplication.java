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
@TableName("adopt_application")
public class AdoptApplication extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer userId; // 用户id
    private Integer adoptRecordId; // 送养记录id
    private String name; // 姓名
    private Integer age; // 年龄
    private String gender; // 性别
    private String phone; // 电话
    private String career; // 职业
    private Integer income; // 月收入区间
    private String reason; // 领养原因
    private String petExp; // 养宠经验
    private String petStatus; // 宠物状态
    private String petKind; // 宠物种类
    private String liveStatus; // 生活状态
    private Integer adoptStatus; // 同意状态 0 未审核 1 未同意 2 同意
}
