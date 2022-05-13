package com.superb.upetstar.common.pojo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; //业务响应码
    private String message; //返回消息
    private Map<String, Object> data = new HashMap<>(); //返回数据

    /**
     * 构造器私有
     */
    private Result() {
    }

    /**
     * 返回成功
     */
    public static Result success() {
        Result r = new Result();
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(ResponseEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 返回失败
     */
    public static Result fail() {
        Result r = new Result();
        r.setCode(ResponseEnum.FAIL.getCode());
        r.setMessage(ResponseEnum.FAIL.getMessage());
        return r;
    }

    /**
     * 设置特定结果
     */
    public static Result setResult(ResponseEnum responseEnum) {
        Result r = new Result();
        r.setCode(responseEnum.getCode());
        r.setMessage(responseEnum.getMessage());
        return r;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}