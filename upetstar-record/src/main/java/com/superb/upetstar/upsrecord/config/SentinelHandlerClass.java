package com.superb.upetstar.upsrecord.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.superb.upetstar.common.pojo.vo.AdoptRecordVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hym
 * @description
 */
public class SentinelHandlerClass {
    public static List<AdoptRecordVO> searchException(String word, BlockException blockException) {
        return new ArrayList<>();
    }

    public static List<AdoptRecordVO> searchError(String word, BlockException blockException) {
        return null;
    }
}
