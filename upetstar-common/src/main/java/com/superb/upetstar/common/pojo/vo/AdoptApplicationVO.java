package com.superb.upetstar.common.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author hym
 * @description
 */
@Data
@ApiModel("领养申请视图对象")
public class AdoptApplicationVO {
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
