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
    /**
     * 根据地址或品种查询
     */
    List<ESPet> findByAddressOrBreed(String address, String breed);

    /**
     * 根据地址或品种分页查询
     */
    Page<ESPet> findByAddressOrBreed(String address, String breed, Pageable pageable);
}
