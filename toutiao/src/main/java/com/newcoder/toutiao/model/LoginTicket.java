package com.newcoder.toutiao.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * created by zsj in 20:38 2018/5/17
 * description:登录token记录
 **/
@Getter
@Setter
@ToString
public class LoginTicket {
    /**
     * id: ticket的id，自增，独一无二
     * userId: 这个ticket属于哪个用户
     * expired: 过期时间，每次会判断当前时间是否超过过期时间，如果超过则ticket无效
     * status: 0表示有效，1表示无效
     * ticket: 相当于token
     */
    private int id;
    private int userId;
    private Date expired;
    private int status;
    private String ticket;
}
