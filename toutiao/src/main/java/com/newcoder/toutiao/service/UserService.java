package com.newcoder.toutiao.service;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.toutiao.dao.LoginTicketDAO;
import com.newcoder.toutiao.dao.UserDAO;
import com.newcoder.toutiao.model.LoginTicket;
import com.newcoder.toutiao.model.User;
import com.newcoder.toutiao.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * created by zsj in 11:18 2018/5/13
 * description:
 **/
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    /**
     * 注册，同样一个map用于记录信息，
     * @param userName
     * @param password
     * @return
     */
    public Map<String, Object> register(String userName, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(userName)) {
            map.put("msgUserName","用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msgPassword", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(userName);
        if (user != null) {
            map.put("msgUserName", "用户名已注册");
            return map;
        }
        user = new User();
        user.setName(userName);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(ToutiaoUtil.MD5(password +user.getSalt()));
        userDAO.addUser(user);
        //ticket
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    /**
     * 登录，参数用户名，密码，返回一个map,用于记录登录信息。
     * 比如密码为空，用户名为空这些信息记录在map中直接返回
     * 如果登录成功map中存放userID 和ticket
     * @param userName
     * @param password
     * @return
     */
    public Map<String, Object> login(String userName, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(userName)) {
            map.put("msgUserName","用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msgPassword", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(userName);
        if (user == null) {
            map.put("msgUserName", "用户名不存在");
            return map;
        }

        if (!ToutiaoUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msgpassword", "密码不正确");
            return map;
        }
        map.put("userId", user.getId());
        /**
         * ticket
         */
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    /**
     * 登出，直接将ticket状态设置成1，这条ticket就无效了
     * @param ticket
     */
    public void logout(String ticket) {
        //设置ticket过期
        loginTicketDAO.updateStatus(ticket,1);
    }

    /**
     * 登录时为用户创建一个ticket，默认过期时间为1天，状态为0（有效），
     * @param userId
     * @return
     */
    public String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        loginTicket.setStatus(0);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        loginTicket.setExpired(date);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

}
