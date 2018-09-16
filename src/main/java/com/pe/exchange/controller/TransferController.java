package com.pe.exchange.controller;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.service.TransferService;
import com.pe.exchange.utils.QrCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@Slf4j
@Api(tags = "转入转出")
@RestController
@RequestMapping("transfer")
public class TransferController {

    @Data
    @ApiModel
    public static class TransferBean{
        @ApiModelProperty(value = "目标地址,转换时填,兑换时不填",example = "")
        private String address;
        @ApiModelProperty(value = "转账数量",example = "8000")
        private String amount;
    }

    @Data
    @ApiModel
    public static class ExchangeBean{

        @ApiModelProperty(value = "转账数量",example = "8000")
        private String amount;
    }

    private static final String ORCODE_FORMAT = "JPEG";

    @Autowired
    TransferService transferService;

    @ApiOperation("获取二维码")
    @GetMapping("qrcode")
    public void getQRCode(HttpServletResponse response) {

        String address = transferService.getAddress();
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            BufferedImage test = QrCodeUtil.createQrCode(address);
            ImageIO.write(test, ORCODE_FORMAT, outputStream);
        } catch (Exception e) {
            log.error("获取二维码异常", e);
        }

    }

    @ApiOperation("转账")

    @PostMapping("transfer")
    public Result transfer(@RequestBody TransferBean transferBean) {
        transferService.transfer(transferBean.getAddress(), transferBean.getAmount());
        return Results.success();
    }


    @ApiOperation("兑换")

    @PostMapping("exchange")
    public Result exchange(@RequestBody ExchangeBean exchangeBean) {
        transferService.exchange(exchangeBean.getAmount());
        return Results.success();
    }




}
