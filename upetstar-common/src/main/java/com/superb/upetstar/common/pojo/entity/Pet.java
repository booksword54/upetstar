package com.superb.upetstar.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.superb.upetstar.common.pojo.es.ESPet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * @author hym
 * @description
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("pet")
public class Pet extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private Integer auditId;
    private Integer communityId;
    private String name;
    private String breed;
    private Integer age;
    private String gender;
    private String address; // 社区地址
    private String description; // 宠物描述
    private String images; // 宠物图片
    private String ster; // 宠物绝育情况
    private String vac; // 宠物疫苗认证
    private Integer auditStatus; // 审核状态 0 未审核 1 为通过 2已通过

    /**
     * 转换为对应文档对象
     */
    public ESPet buildESPet() {
        ESPet esPet = new ESPet();
        BeanUtils.copyProperties(this, esPet);
        if (this.getImages() != null) {
            esPet.setImage(StringUtils.split(this.getImages(), ",")[0]);
        }
        return esPet;
    }
}
