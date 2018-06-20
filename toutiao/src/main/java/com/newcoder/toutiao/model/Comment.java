package com.newcoder.toutiao.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * created by zsj in 11:49 2018/5/28
 * description:
 **/
@Getter
@Setter
public class Comment {
    private int id;
    private int userId;
    //entityId对应entityType中某一条内容，可以是新闻，可以是评论，可以是人
    private int entityId;
    //通过entityType来区分是对新闻的评论，还是对评论的评论，还是对人的评论
    private int entityType;
    private String content;
    private Date createdDate;
    //想要删除的话直接更改status,而不是直接删除，1表示无效（已删除）,0表示有效
    private int status;
}
