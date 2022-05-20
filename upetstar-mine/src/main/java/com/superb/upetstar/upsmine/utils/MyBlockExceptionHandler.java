package com.superb.upetstar.upsmine.utils;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.superb.upetstar.common.pojo.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, BlockException e) throws Exception {
        // getRule() 资源  规则的详细信息
        log.info("BlockExceptionHandler BlockException================" + e.getRule());

        Result r = null;

        if (e instanceof FlowException) {
            r = Result.fail().code(100).message("接口限流了");

        } else if (e instanceof DegradeException) {
            r = Result.fail().code(101).message("服务降级了");

        } else if (e instanceof ParamFlowException) {
            r = Result.fail().code(102).message("热点参数限流了");

        } else if (e instanceof SystemBlockException) {
            r = Result.fail().code(103).message("触发系统保护规则了");

        } else if (e instanceof AuthorityException) {
            r = Result.fail().code(104).message("授权规则不通过");
        }

        //返回json数据
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), r);
    }
}
