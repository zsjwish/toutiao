package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.model.HostHolder;
import com.newcoder.toutiao.model.Message;
import com.newcoder.toutiao.model.User;
import com.newcoder.toutiao.model.ViewObject;
import com.newcoder.toutiao.service.MessageService;
import com.newcoder.toutiao.service.UserService;
import com.newcoder.toutiao.util.ToutiaoUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * created by zsj in 16:18 2018/5/28
 * description:
 **/
@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    /**
     * 增加一条message
     * @param fromId：发信ID
     * @param toId：收新ID（可以是news，可以是user等）
     * @param content：信的内容
     * @return
     */
    @RequestMapping(value = "/msg/addMessage", method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message message = new Message();
            message.setFromId(fromId);
            message.setToId(toId);
            message.setContent(content);
            message.setCreatedDate(new Date());
//            message.setConversationId(fromId > toId ? String.format("%d_%d",toId,fromId) : String.format("%d_%d",fromId,toId));
            messageService.addMessage(message);
            return ToutiaoUtil.getJSONString(0,"插入评论成功");
        }catch (Exception e) {
            logger.error("消息发送失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1,"插入评论失败");
        }
    }

    /**
     * 获取关于某个conversationId的message列表
     * 用在用户点击进入和某一个确定用户的消息列表使用，
     * 首先在消息栏会看到和不同的人的消息往来和未读消息，点击后会调用这个函数返回与某一个人的具体消息，
     * @param model
     * @param conversationId
     * @return
     */
    @RequestMapping(value = "/msg/detail", method = {RequestMethod.GET})
    public String conversationDetail(Model model, @Param("conversationId") String conversationId) {
        try {
            List<Message> conversationList = messageService.getConversationDetail(conversationId,0,10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User user = userService.getUser(msg.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        }catch (Exception e) {
            logger.error("查询message的detail出错" + e.getMessage());
        }
        return "letterDetail";
    }

    /**
     * 登录状态下查看和自己相关的消息列表，如果没登录应该提示其登录,list中一条数据是与一个人的会话
     * @param model
     * @return
     */
    @RequestMapping(value = "/msg/list", method = RequestMethod.GET)
    public String conversationList(Model model) {
        try {
            User user = hostHolder.getUser();
            if (user == null) {
                //判断当前没有登录的状态
            }
            List<Message> conversationList = messageService.getConversationList(user.getId(), 0,10);
            List<ViewObject> conversations = new ArrayList<>();
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", msg);
                int targetId = msg.getFromId() == user.getId() ? msg.getToId() : msg.getFromId();
                User targetUser = userService.getUser(targetId);
                vo.set("user", targetUser);
                vo.set("unread", messageService.getConversationUnreadCount(user.getId(), msg.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
        }catch (Exception e) {
            logger.error("查询消息列表出错" + e.getMessage());
        }
        return "letter";
    }
}
