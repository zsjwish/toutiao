package com.newcoder.toutiao.async;

import java.util.List;

/**
 * created by zsj in 15:25 2018/5/31
 * description:
 **/
public interface EventHandler {
    //抽象函数，处理事件
    void doHandle(EventModel model);
    //需要处理那些事件列表，只要是列表中的事件，都需要处理，就是这个处理类需要处理的事件列表
    List<EventType> getSupportEventType();
}
