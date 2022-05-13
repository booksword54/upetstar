package com.superb.upetstar.repository;

import com.superb.upetstar.common.pojo.es.ESPet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author hym
 * @description
 */
public interface PetRepository extends ElasticsearchRepository<ESPet, Integer> {

}
