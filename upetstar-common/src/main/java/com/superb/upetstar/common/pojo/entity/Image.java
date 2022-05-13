package com.superb.upetstar.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hym
 * @description
 */
@Data
@Accessors(chain = true)
@TableName("image")
public class Image {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String url;
}
