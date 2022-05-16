package com.superb.upetstar.common.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hym
 * @description
 */
@Data
@ApiModel("宠物详情信息视图对象")
public class PetDetailVO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String[] images; // 宠物图像路径数组
    private String avatarUrl; // 宠物头像路径
    private String name; // 宠物姓名
    private Date publishTime; // 宠物创建时间
    private String description; // 宠物描述
    private Integer age; // 年龄
    private String gender; // 性别
    private String breed; // 种类
    private String ster; // 绝育信息
    private String vac; // 疫苗信息
}
