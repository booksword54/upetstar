package com.superb.upetstar.common.pojo.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author hym
 * @description ES宠物文档对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "pet", createIndex = true, shards = 1, replicas = 1)
public class ESPet {
    @Id
    private Integer id; // 文档id/宠物id
    @Field(type = FieldType.Keyword, index = false)
    private String name; // 宠物名
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String breed; // 宠物品种
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String address; // 社区地址
    @Field(type = FieldType.Keyword, index = false)
    private String image; // 宠物第一张图
}
