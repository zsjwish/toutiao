package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.model.*;
import com.newcoder.toutiao.service.CommentService;
import com.newcoder.toutiao.service.LikeService;
import com.newcoder.toutiao.service.NewsService;
import com.newcoder.toutiao.service.UserService;
import com.newcoder.toutiao.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * created by zsj in 15:54 2018/5/27
 * description:
 **/
@Controller
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    NewsService newsService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;


    /**
     * 获取图片，通过图片名字获取到图片存放在HttpServletResponse中返回给页面
     * @param imageName
     * @param response
     */
    @RequestMapping(path = "/image", method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response) {
        try {
            //设置返回的类型是image/jpeg
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.IMAGE_DIR + imageName)),
                    response.getOutputStream());
        }catch (Exception e) {
            logger.error("图片获取错误" + e.getMessage());
        }
    }

    /**
     * 上传图片到本地。
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadImage/", method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file")MultipartFile file) {
        try {
            String fileUrl = newsService.saveImage(file);
            if (fileUrl == null) {
                return ToutiaoUtil.getJSONString(1,"上传图片失败");
            }
            return ToutiaoUtil.getJSONString(0, fileUrl);
        }catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1,"上传失败");
        }
    }

    @RequestMapping(value = "/user/addNews/", method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link) {
        try {
            News news = new News();
            news.setImage(image);
            news.setTitle(title);
            news.setLink(link);
            news.setCreatedDate(new Date());
            if (hostHolder.getUser()!=null) {
                news.setUserId(hostHolder.getUser().getId());
            }
            else {
                //如果hostHolder中没有存储user,则以匿名的形式发送
                news.setUserId(3);
            }
            newsService.addNews(news);
            return ToutiaoUtil.getJSONString(0, "资讯上传成功");
        }catch (Exception e) {
            logger.error("资讯录入失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1,"发布失败");
        }
    }

    /**
     * 资讯详情，通过newsId连接到某新闻的详情页面上去，通过Model传到前端在界面上显示数据
     * @param newsId
     * @param model
     * @return
     */
    @RequestMapping(value = "/news/{newsId}", method = {RequestMethod.GET,RequestMethod.POST})
    public String newsDetail(@PathVariable("newsId") int newsId,
                             Model model) {
        News news = newsService.getById(newsId);
        if (news != null) {
            int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
            if (localUserId != 0) {
                model.addAttribute("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                model.addAttribute("like", 0);
            }
            //评论
            List<Comment> comments = commentService.getCommentByEntity(news.getId(), EntityType.ENTITY_NEWS);
            List<ViewObject> commentVOs = new ArrayList<>();
            for (Comment comment : comments) {
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);
                vo.set("user", userService.getUser(comment.getUserId()));
            }
            model.addAttribute("comments", commentVOs);
        }
        model.addAttribute("news",news);
        //将用户user放在owner参数中，前端用owner
        model.addAttribute("owner",userService.getUser(news.getUserId()));
        //返回detail.html页面
        return "detail";
    }

    @RequestMapping(value = "/addComment/",method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setUserId(hostHolder.getUser().getId());
            comment.setContent(content);
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);
            commentService.addComment(comment);
            //更新news里面评论的数量
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            newsService.updateCommentCountById(comment.getEntityId(), count);
            //异步

        }catch (Exception e ) {
            logger.error("添加评论出错" + e.getMessage());
        }
        return "redirect:/news/"+String.valueOf(newsId);
    }
}
