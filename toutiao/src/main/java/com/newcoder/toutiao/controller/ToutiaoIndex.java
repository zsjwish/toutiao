package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.service.ToutiaoService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * created by zsj in 19:58 2018/5/9
 * description:
 **/
//@Controller
public class ToutiaoIndex {

    @Autowired
    ToutiaoService toutiaoService;

//    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
//    @ResponseBody
//    public String index(HttpSession session) {
//        return "这是头条资讯，请好好学习" + session.getAttribute("msg") + toutiaoService.say();
//    }

    @RequestMapping(path = "/profile/{groupID}/{userID}", method = RequestMethod.GET)
    @ResponseBody
    public String profile(@PathVariable("groupID") String groupid,
                          @PathVariable("userID") String userID,
                          @RequestParam(value = "type", defaultValue = "zsj") String type,
                          @RequestParam(value = "key", defaultValue = "key") String key
    ) {
        return String.format("PathVariable在路径中是一定要写的，RequestParam可以不写，不写就是默认的值<br>" +
                "groupID : %s <br>" +
                "userID : %s <br>" +
                "key : %s <br>" +
                "type : %s <br>", groupid, userID, key, type);
    }

    @RequestMapping(value = "/request")
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }

        for (Cookie cookie : request.getCookies()) {
            sb.append("CookieName:");
            sb.append(cookie.getName());
            sb.append(" : ");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }
        sb.append("getMethod: " + request.getMethod() + "<br>");
        sb.append("getPathInfo: " + request.getPathInfo() + "<br>");
        sb.append("getQueryString: " + request.getQueryString() + "<br>");
        sb.append("getRequestURL: " + request.getRequestURI() + "<br>");

        return sb.toString();
    }

    @RequestMapping(value = "/response")
    @ResponseBody
    public String response(@CookieValue(value = "coder", defaultValue = "hello") String coder,
                           @RequestParam(value = "key", defaultValue = "coder") String key,
                           @RequestParam(value = "value", defaultValue = "value") String value,
                           HttpServletResponse response) {
        response.addCookie(new Cookie(key, value));
        response.addHeader(key, value);
        return "newCoder  + Cookie   :" + coder;
    }

//    @RequestMapping(value = "/redirect/{code}")
//    public RedirectView redirect(@PathVariable(value = "code") int code) {
//        RedirectView redirectView = new RedirectView("/response");
//        if (code == 301) {
//            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
//        }
//        return redirectView;
//
//    }
    @RequestMapping(value = "/redirect/{code}")
    public String redirect(@PathVariable(value = "code") int code,
                                 HttpSession session) {
        session.setAttribute("msg", "redirect from others");
        return "redirect:/";
    }
}
