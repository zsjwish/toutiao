package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.async.EventModel;
import com.newcoder.toutiao.async.EventProducer;
import com.newcoder.toutiao.async.EventType;
import com.newcoder.toutiao.async.handler.LoginExceptionHandler;
import com.newcoder.toutiao.service.UserService;
import com.newcoder.toutiao.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * created by zsj in 20:47 2018/5/16
 * description:
 **/
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = "/reg/", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String register(Model model, @RequestParam("userName") String userName,
                           @RequestParam("password") String password,
                           @RequestParam(value = "remember", defaultValue = "0") int remember,
                           HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(userName, password);
            if (map.containsValue("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (remember > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0, "注册成功");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册失败");
        }
    }

    /**
     * 登录，需要参数用户名，密码，（可选是否记住密码）
     *
     * @param model
     * @param userName
     * @param password
     * @param remember
     * @param response
     * @return
     */
    @RequestMapping(path = "/login/", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam(value = "remember", defaultValue = "0") int remember,
                        HttpServletResponse response) {
        try {
            logger.info(String.format("用户%s登录，密码是%s",userName, password));
            Map<String, Object> map = userService.login(userName, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (remember > 0) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                eventProducer.fireEvent(new EventModel(EventType.LOGIN)
                        .setActorId((int)map.get("userId")).setExt("username",userName)
                        .setExt("email","zsjwish@126.com"));
                return ToutiaoUtil.getJSONString(0, "登录成功");
            } else {
                return ToutiaoUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "登陆失败");
        }
    }

    /**
     * 登出，清理ticket,然后重定向到/主页面
     * @param ticket
     * @return
     */
    @RequestMapping(path = "/logout/", method = {RequestMethod.GET,RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
}
