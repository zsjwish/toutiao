package com.newcoder.toutiao.util;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 * created by zsj in 20:08 2018/5/31
 * description:邮件服务
 **/
@Service
public class MailSender implements InitializingBean {


    @Autowired
    private VelocityEngine velocityEngine;

    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    //java发送邮件类
    private JavaMailSenderImpl mailSender;
    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("1023450196@qq.com");
        mailSender.setPassword("qnfzzitkyjxvbfbg");
        mailSender.setHost("smtp.qq.com");
//        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        //邮件属性
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        //添加邮件属性
        mailSender.setJavaMailProperties(javaMailProperties);
    }

    /**
     *
     * @param to 发送给谁
     * @param subject 主题 就是标题
     * @param template 模板
     * @param model 发送的内容
     * @return
     */
    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model) {
        try {
            //发送人昵称
            String nick = MimeUtility.encodeText("牛客中级课");
            //发送地址（昵称加邮箱）
            InternetAddress from = new InternetAddress(nick + "<1023450196@qq.com>");
            //创建一封邮件
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            //使用velocity模板创建邮件。
            String result = VelocityEngineUtils
                    .mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
            //发送到谁的邮箱
            mimeMessageHelper.setTo(to);
            //发件人是谁
            mimeMessageHelper.setFrom(from);
            //邮件主题是什么
            mimeMessageHelper.setSubject(subject);
            //邮件内容，就是通过velocity渲染后的内容
            mimeMessageHelper.setText(result, true);
            //发送邮件
            mailSender.send(mimeMessage);
            logger.info("发送邮件成功");
            return true;
        } catch (Exception e) {
            System.out.println("发送邮件失败" + e.getMessage());
            logger.error("发送邮件失败" + e.getMessage());
            return false;
        }
    }
}
