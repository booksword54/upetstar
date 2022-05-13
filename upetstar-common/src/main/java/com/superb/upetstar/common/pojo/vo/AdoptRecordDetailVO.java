package com.superb.upetstar.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hym
 * @description
 */
@Data
@ApiModel("送养记录详情视图对象")
public class AdoptRecordDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer adoptRecordId;
    private String avatarUrl; // 头像url
    private String username; // 用户姓名
    private String[] images; // 宠物图片路径数组
    private Integer age; // 宠物年龄
    private String gender; // 宠物性别
    private String breed; // 宠物种类
    private String ster; // 宠物绝育状态
    private String vac; // 宠物疫苗认证
    private String description; // 送养信息
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date giveTime; // 领/送养时间
}

