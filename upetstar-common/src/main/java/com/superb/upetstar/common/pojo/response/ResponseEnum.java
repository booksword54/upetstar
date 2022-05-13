package com.superb.upetstar.common.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum ResponseEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "失败"),
    LOGIN_ERROR(501, "登录失败");

    private Integer code;//状态码
    private String message;//消息
}