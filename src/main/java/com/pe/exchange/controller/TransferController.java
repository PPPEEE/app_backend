package com.pe.exchange.controller;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.entity.DKDealInfo;
import com.pe.exchange.service.TransferService;
import com.pe.exchange.utils.QrCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
@Slf4j
@Api(tags = "转入转出")
@RestController
@RequestMapping("transfer")
public class TransferController {

		private Logger log = LoggerFactory.getLogger(TransferController.class);
        private static  final  String ORCODE_FORMAT="JPEG";

        @Autowired TransferService transferService;

        @ApiOperation("获取二维码")
        @GetMapping("qrcode")
        public void getQRCode(HttpServletResponse response){

            String address = transferService.getAddress();
            try(ServletOutputStream outputStream = response.getOutputStream()) {
                BufferedImage test = QrCodeUtil.createQrCode(address);
                ImageIO.write(test,ORCODE_FORMAT,outputStream);
            } catch (Exception e) {
                log.error("获取二维码异常",e);
            }

        }

        @ApiOperation("转账")
        @PostMapping("transfer")
        public Result transfer(@RequestParam("address") String address,@RequestParam("amount") String amount){
                transferService.transfer(address,amount);
                return Results.success();
        }
}
