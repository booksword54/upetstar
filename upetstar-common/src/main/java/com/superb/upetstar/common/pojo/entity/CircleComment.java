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
@TableName("circle_comment")
public class CircleComment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private Integer blogId;
    private String content;
    private Integer likeNum;
}
