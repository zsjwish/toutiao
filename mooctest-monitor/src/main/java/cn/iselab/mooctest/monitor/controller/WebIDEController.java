package cn.iselab.mooctest.monitor.controller;

import cn.iselab.mooctest.monitor.service.WebIDEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by zsj in 20:24 2018/6/5
 * description:
 **/
@Controller
public class WebIDEController {
    @Autowired
    WebIDEService webIDEService;

    @RequestMapping(value = "/webIDE/answerTimes/", method = RequestMethod.GET)
    @ResponseBody
    public Integer getIDETotalAnswerTimes() {
        return webIDEService.getTotalWebIDEUser();
    }

    @RequestMapping(value = "/webIDE/onlineNumbers/", method = RequestMethod.GET)
    @ResponseBody
    public Integer getOnlineNumbers() {
        return webIDEService.getOnlineNumbers();
    }

    @RequestMapping(value = "/webIDE/dailyLogin/", method = RequestMethod.GET)
    @ResponseBody
    public Integer getDailyLoginWebIDE() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String createdDate = sf.format(new Date());
        System.out.println(createdDate);
        return webIDEService.getDailyLoginWebIDE(createdDate);
    }


}
