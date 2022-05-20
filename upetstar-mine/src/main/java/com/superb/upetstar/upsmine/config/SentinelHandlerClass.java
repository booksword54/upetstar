package com.superb.upetstar.upsmine.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.superb.upetstar.common.pojo.entity.AdoptApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hym
 * @description
 */
public class SentinelHandlerClass {

    public static int updateByAdoptApplyException(AdoptApplication adoptApplication, BlockException blockException) {
        return 0;
    }

    public static int updateByAdoptApplyError(AdoptApplication adoptApplication, BlockException blockException) {
        return 0;
    }

}
