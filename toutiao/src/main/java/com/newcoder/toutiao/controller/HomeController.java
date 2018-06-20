package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.dao.UserDAO;
import com.newcoder.toutiao.model.EntityType;
import com.newcoder.toutiao.model.HostHolder;
import com.newcoder.toutiao.model.News;
import com.newcoder.toutiao.model.ViewObject;
import com.newcoder.toutiao.service.LikeService;
import com.newcoder.toutiao.service.NewsService;
import com.newcoder.toutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * created by zsj in 11:16 2018/5/13
 * description:
 **/

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    /**
     * 通过一个list展示news，使用list存放展示的ViewObject，
     * @param userId：获取某个用户发布的新闻
     * @param offset：偏移量，从第几条新闻开始取
     * @param limit：取几条新闻
     * 然后放在list vos中，传给前端展示用
     * @return
     */
    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);
        int localUserId = hostHolder.getUser()!=null?hostHolder.getUser().getId():0;
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            }
            else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 主页面，路径为/或者index/,通过model传给前端，将vos作为一个参数，把getNews获取的数据放在vos中
     * return home 是返回home.html页面。
     * @param model
     * @return
     */
    @RequestMapping(value = {"/","/index"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String index(Model model) {

        model.addAttribute("vos",getNews(0,0,10));
        return "home";
    }

    /**
     * 通过user/{userId}查询news,pop=0表示没有登录，为1表示已经登录，这个在前端有判定
     * @param model
     * @param userId
     * @param pop
     * @return
     */
    @RequestMapping(value = {"/user/{userId}"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId,
                            @RequestParam(value = "pop",defaultValue = "0") int pop) {

        model.addAttribute("vos",getNews(userId,0,10));
        model.addAttribute("pop",pop);
        return "home";
    }

}
