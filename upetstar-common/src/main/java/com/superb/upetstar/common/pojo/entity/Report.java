package com.superb.upetstar.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hym
 * @description
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("report")
public class Report extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private Integer type;
    private Integer targetId;
    private String description; // 举报信息
    private String images; // 举报图片 以，分割
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reportTime; // 举报日期
}
