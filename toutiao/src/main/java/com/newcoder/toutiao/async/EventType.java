package com.newcoder.toutiao.async;

/**
 * created by zsj in 15:07 2018/5/31
 * description: 事件类型，就是发生了什么事件
 **/
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
