package com.superb.upetstar.common.exception;

import com.superb.upetstar.common.pojo.response.ResponseEnum;
import com.superb.upetstar.common.pojo.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author hym
 * @description
 */
@Slf4j
@Component
@RestControllerAdvice
public class UPetStarExceptionHandler {

    @ExceptionHandler(value = UPetStarLoginException.class)
    public Result error(UPetStarLoginException e) {
        Result result = Result.setResult(ResponseEnum.LOGIN_ERROR);
        String message = (e.getMessage() != null ? e.getMessage() : "出现UPetStarLoginException异常");
        result.message(message);
        log.error(message, e);
        return result;
    }

    @ExceptionHandler(value = UPetStarException.class)
    public Result error(UPetStarException e) {
        Result result = Result.fail();
        String message = (e.getMessage() != null ? e.getMessage() : "出现Exception异常");
        result.message(message);
        log.error(message, e);
        return result;
    }

    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e) {
        Result result = Result.fail();
        String message = (e.getMessage() != null ? e.getMessage() : "出现Exception异常");
        result.message(message);
        log.error(message, e);
        return result;
    }

}
