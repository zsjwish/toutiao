package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.async.EventModel;
import com.newcoder.toutiao.async.EventProducer;
import com.newcoder.toutiao.async.EventType;
import com.newcoder.toutiao.model.EntityType;
import com.newcoder.toutiao.model.HostHolder;
import com.newcoder.toutiao.model.News;
import com.newcoder.toutiao.service.LikeService;
import com.newcoder.toutiao.service.NewsService;
import com.newcoder.toutiao.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * created by zsj in 21:31 2018/5/29
 * description:
 **/
@Controller
public class LikeController {

    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);


    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    @Autowired
    NewsService newsService;

    @Autowired
    EventProducer eventProducer;

    /**
     * 对于某条新闻，如果点击了喜欢，那么hostHolder获取userID，然后通过like()把这个用户加到喜欢列表中，
     * 然后更新news里面的喜欢数量，最后返回json串；
     *
     * @param newsId
     * @return
     */
    @RequestMapping(value = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId) {
        int userId = hostHolder.getUser().getId();
        News news = newsService.getById(newsId);
        long likeCount = likeService.like(userId, EntityType.ENTITY_NEWS, newsId);
        newsService.updateLikeCountById(newsId, (int) likeCount);
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityType(EntityType.ENTITY_NEWS)
                .setEntityId(newsId).setEntityOwneId(news.getUserId()));
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(value = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String disLike(@RequestParam("newsId") int newsId) {
        int userId = hostHolder.getUser().getId();
        long likeCount = likeService.disLike(userId, EntityType.ENTITY_NEWS, newsId);
        newsService.updateLikeCountById(newsId, (int) likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

}
