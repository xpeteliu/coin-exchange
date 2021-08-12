package io.github.xpeteliu.controller;

import io.github.xpeteliu.entity.Sms;
import io.github.xpeteliu.model.R;
import io.github.xpeteliu.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Text-sending features will not be really implemented since they rely on paid third-party services
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    SmsService smsService;

    @GetMapping("/sendTo")
    public R sendSms(Sms sms) {
        if(smsService.sendSms(sms)){
            return R.success();
        }else {
            return R.failure();
        }
    }
}
