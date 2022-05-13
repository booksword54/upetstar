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
@ApiModel("领送养记录视图对象")
public class AdoptRecordVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer adoptRecordId; // 领养记录id
    private String title; // 标题
    private String description; // 描述
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date giveTime; // 送养（领养）时间

    private String breed; // 宠物种类
    private String address; // 宠物地址
    private String image; // 宠物的第一张图

}
