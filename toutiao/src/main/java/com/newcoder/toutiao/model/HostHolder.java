package com.newcoder.toutiao.model;

import org.springframework.stereotype.Component;

/**
 * created by zsj in 18:03 2018/5/19
 * description: 确定某一个访问里面，是哪个用户在访问。
 **/
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
