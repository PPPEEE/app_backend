package com.pe.exchange.config;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex) {
        log.error("服务器异常",ex);
        return Results.fail("服务器异常,请稍候再试!");
    }

    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public Result myErrorHandler(BaseException ex) {
        return  ex.getResult();
    }

}
