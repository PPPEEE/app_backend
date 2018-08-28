package com.pe.exchange.config;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.exception.BaseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ControllerExceptionAdvice {


    /**
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public Result myErrorHandler(BaseException ex) {
        return  Results.fail(ex);
    }

}
