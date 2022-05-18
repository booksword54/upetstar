package com.superb.upetstar.upsrecord.repository;

import com.superb.upetstar.common.pojo.es.ESAdoptRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author hym
 * @description
 */
public interface AdoptRecordRepository extends ElasticsearchRepository<ESAdoptRecord, Integer> {
    /**
     * 根据标题或描述查询
     */
    List<ESAdoptRecord> findByTitleOrDescription(String title, String description);

    /**
     * 根据标题或描述分页查询
     */
    Page<ESAdoptRecord> findByTitleOrDescription(String title, String description, Pageable pageable);
}
