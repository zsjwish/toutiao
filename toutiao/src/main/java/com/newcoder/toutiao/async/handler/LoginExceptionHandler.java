package com.newcoder.toutiao.async.handler;

import com.newcoder.toutiao.async.EventHandler;
import com.newcoder.toutiao.async.EventModel;
import com.newcoder.toutiao.async.EventType;
import com.newcoder.toutiao.model.Message;
import com.newcoder.toutiao.service.MessageService;
import com.newcoder.toutiao.util.MailSender;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * created by zsj in 19:47 2018/5/31
 * description:
 **/
@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {
        //判断是否有异常登录
        Message message = new Message();
        message.setToId(model.getActorId());
        message.setContent("你上次登录IP异常");
        message.setFromId(3);
        message.setCreatedDate(new Date());
        messageService.addMessage(message);

        Map<String, Object> map = new HashedMap();
        map.put("username",model.getExt("username"));
        map.put("email", model.getExt("email"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"),"异常登录", "mails/welcome.html",map);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LOGIN);
    }
}
