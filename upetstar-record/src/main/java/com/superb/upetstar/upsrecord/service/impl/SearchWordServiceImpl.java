package com.superb.upetstar.upsrecord.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superb.upetstar.common.utils.DateUtils;
import com.superb.upetstar.upsrecord.mapper.SearchWordMapper;
import com.superb.upetstar.common.pojo.entity.AdoptRecord;
import com.superb.upetstar.common.pojo.entity.Pet;
import com.superb.upetstar.common.pojo.entity.SearchWord;
import com.superb.upetstar.common.pojo.es.ESAdoptRecord;
import com.superb.upetstar.common.pojo.es.ESPet;
import com.superb.upetstar.common.pojo.vo.AdoptRecordVO;
import com.superb.upetstar.upsrecord.repository.AdoptRecordRepository;
import com.superb.upetstar.upsrecord.repository.PetRepository;
import com.superb.upetstar.service.IAdoptRecordService;
import com.superb.upetstar.service.IPetService;
import com.superb.upetstar.service.ISearchWordService;
import com.superb.upetstar.upsrecord.utils.SearchRedisHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hym
 * @description
 */
@DubboService
public class SearchWordServiceImpl extends ServiceImpl<SearchWordMapper, SearchWord> implements ISearchWordService {

    @DubboReference
    private IPetService petService;
    @Resource
    private IAdoptRecordService adoptRecordService;
    @Resource
    private PetRepository petRepository;
    @Resource
    private AdoptRecordRepository adoptRecordRepository;
    @Resource
    private SearchRedisHelper searchRedisHelper;

    @Override
    public List<AdoptRecordVO> search(String word) {
        if (StringUtils.isEmpty(word)) {
            return new ArrayList<>();
        }
        // 关键字[标题、描述]查询送养信息
        List<ESAdoptRecord> esAdoptRecords = adoptRecordRepository.findByTitleOrDescription(word, word);
        // 根据送养信息查询对应宠物信息，合并成视图对象流
        List<AdoptRecordVO> adoptRecordVOS1 = esAdoptRecords.stream()
                .map(esAdoptRecord -> {
                    Integer petId = esAdoptRecord.getPetId(); // 从文档中获取宠物id
                    Pet pet = petService.getById(petId); // 查询宠物信息
                    AdoptRecordVO adoptRecordVO = new AdoptRecordVO();
                    // 封装送养记录相关信息
                    adoptRecordVO.setAdoptRecordId(esAdoptRecord.getId());
                    adoptRecordVO.setTitle(esAdoptRecord.getTitle());
                    adoptRecordVO.setDescription(esAdoptRecord.getDescription());
                    adoptRecordVO.setGiveTime(DateUtils.parseDate(esAdoptRecord.getGiveTime(), "yyyy-MM-dd"));
                    // 封装宠物相关信息
                    adoptRecordVO.setBreed(pet.getBreed());
                    adoptRecordVO.setAddress(pet.getAddress());
                    if (pet.getImages() != null) {
                        adoptRecordVO.setImage(StringUtils.split(pet.getImages(), ",")[0]);
                    }
                    return adoptRecordVO;
                }).collect(Collectors.toList());

        // 关键字[地址，名字]查询宠物信息
        List<ESPet> esPets = petRepository.findByAddressOrBreed(word, word);
        // 根据宠物信息查询对应送养记录信息，合并成视图对象流
        List<AdoptRecordVO> adoptRecordVOS = new ArrayList<>();
        esPets.stream().peek(esPet -> {
            Integer petId = esPet.getId();
            QueryWrapper<AdoptRecord> wrapper = new QueryWrapper<>();
            // 查询宠物领养记录
            List<AdoptRecord> adoptRecords = adoptRecordService.list(wrapper.eq("pet_id", petId));
            // 封装进视图对象
            for (AdoptRecord adoptRecord : adoptRecords) {
                AdoptRecordVO adoptRecordVO = new AdoptRecordVO();
                // 封装送养记录id
                adoptRecordVO.setAdoptRecordId(adoptRecord.getId());
                adoptRecordVO.setTitle(adoptRecord.getTitle());
                adoptRecordVO.setDescription(adoptRecord.getDescription());
                adoptRecordVO.setGiveTime(adoptRecord.getGiveTime());
                // 封装宠物信息
                adoptRecordVO.setImage(esPet.getImage());
                adoptRecordVO.setBreed(esPet.getBreed());
                adoptRecordVO.setAddress(esPet.getAddress());
                adoptRecordVOS.add(adoptRecordVO);
            }
        }).close();
        // 合并视图对象
        adoptRecordVOS.addAll(adoptRecordVOS1);
        //searchRedisHelper.addRedisHotSearch(adoptRecordVOS); // 添加热门搜索记录
        searchRedisHelper.searchZincrby(word);
        return adoptRecordVOS;
    }

    @Override
    public Set<String> listHotWord(Integer start, Integer end) {
        if (start == null) {
            start = 0;
        }
        if (end == null) {
            end = 10;
        }
        Set<ZSetOperations.TypedTuple<String>> set = searchRedisHelper.queryTopSearchHotWord(start, end);
        Set<String> hotword = set.stream().map(ZSetOperations.TypedTuple::getValue).collect(Collectors.toSet());
        return hotword;

    }

    @Override
    public Set<AdoptRecordVO> listHotSearch() {
        return searchRedisHelper.listHotSearch();
    }

    @Override
    public void deleteSearchCache(String keyName) {
        if (StringUtils.isEmpty(keyName)) {
            return;
        }
        searchRedisHelper.deleteKey(keyName);
    }

}
