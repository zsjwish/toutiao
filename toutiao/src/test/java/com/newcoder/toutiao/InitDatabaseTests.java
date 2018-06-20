package com.newcoder.toutiao;

import com.newcoder.toutiao.dao.CommentDAO;
import com.newcoder.toutiao.dao.LoginTicketDAO;
import com.newcoder.toutiao.dao.NewsDAO;
import com.newcoder.toutiao.dao.UserDAO;
import com.newcoder.toutiao.model.*;
import com.newcoder.toutiao.util.ToutiaoUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

/**
 * created by zsj in 20:19 2018/5/12
 * description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {

    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    CommentDAO commentDAO;

    @Test
    public void contextLoads() {
        Random random = new Random();
        //userDAO.insert
        for (int i = 1; i <= 15; i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            String password = "123456789";
            user.setPassword(ToutiaoUtil.MD5(password));
            user.setSalt("");
            userDAO.addUser(user);

            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            News news = new News();
            news.setTitle(String.format("Title%d", i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            news.setImage(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            news.setLikeCount(i+1);
            news.setCommentCount(i*i);
            news.setCreatedDate(date);
            news.setUserId(i+1);
            newsDAO.addNews(news);

            for (int j = 0; j < 3; j++) {
                Comment comment = new Comment();
                comment.setUserId(i+1);
                comment.setEntityId(news.getId());
                comment.setEntityType(EntityType.ENTITY_NEWS);
                comment.setCreatedDate(new Date());
                comment.setStatus(0);
                comment.setContent("Comment" + String.valueOf(i));
                commentDAO.addComment(comment);
            }

            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i+1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("TICKET%d",i));
            loginTicketDAO.addTicket(ticket);

            loginTicketDAO.updateStatus(ticket.getTicket(), 2);
        }
        //userDao.getById
        Assert.assertEquals("USER10", userDAO.selectById(10).getName());
        //userDao.selectByName
        Assert.assertEquals(5, userDAO.selectByName("USER5").getId());
        //userDAO.update()
        User user = userDAO.selectById(1);
        user.setPassword("yllshi");
        userDAO.updatePassword(user);
        Assert.assertEquals("yllshi", userDAO.selectById(1).getPassword());
        //userDAO.delete
        userDAO.deleteById(2);
        Assert.assertEquals(null, userDAO.selectById(2));

        //newsDAO.insert
//        Assert.assertEquals("Title11", newsDAO.);

        Assert.assertEquals(2, loginTicketDAO.selectByTicket("TICKET1").getUserId());
        Assert.assertEquals(2, loginTicketDAO.selectByTicket("TICKET1").getStatus());

        Assert.assertNotNull(commentDAO.selectByEntity(1,EntityType.ENTITY_NEWS).get(0));


    }
}
