package com.superb.upetstar.upsmine.repository;

import com.superb.upetstar.common.pojo.es.ESPet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author hym
 * @description
 */
public interface PetRepository extends ElasticsearchRepository<ESPet, Integer> {

}
