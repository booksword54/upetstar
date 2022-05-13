package com.superb.upetstar.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author hym
 * @description
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("circle_blog")
public class CircleBlog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String username;
    private String content;
    private String images;
    private Integer likeNum;
    private Integer collectNum;
    private Integer commentNum;
    @TableField(exist = false)
    private List<Integer> imageList;
}
