package com.newcoder.toutiao.async;

import com.alibaba.fastjson.JSON;
import com.newcoder.toutiao.util.JedisAdapter;
import com.newcoder.toutiao.util.RedisKeyUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by zsj in 15:45 2018/5/31
 * description:消费者，将序列化传过来之后的JSON转为EventModel,从EventModel中的类型触发相应的事件处理
 **/
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware{

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    //这个map存储事件类型和处理列表，表示这个事件类型对应的需要处理的handler列表。
    private Map<EventType, List<EventHandler>> config = new HashedMap();
    //获取上下文信息
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        //把所有实现了EventHandler接口的类存在map中,key是Handler的名字，value是这个Handler要处理的事件的Handler类
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
            //获取handler类支持的要处理的时间类型，存放在list中，
            List<EventType> eventTypes = entry.getValue().getSupportEventType();
            //对于每一个事件类型，如果config中没有这个key,则先添加这个事件类型到key中，然后把这个事件的处理类放在value中，
            for (EventType type : eventTypes) {
                if (!config.containsKey(type)) {
                    config.put(type, new ArrayList<>());
                }
                config.get(type).add(entry.getValue());
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //获取队列的key名
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);
                    for (String message : events) {
                        //因为brpop返回值是这个key,所以这个应该过滤掉，假如在指定时间内没有任何元素被弹出，
                        // 则返回一个 nil 和等待时长。 反之，返回一个含有两个元素的列表，
                        // 第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
                        if (message.equals(key)) {
                            continue;
                        }
                        //将获取的事件JSON串解析为eventModel类，
                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if (!config.containsKey(eventModel.getType())) {
                            logger.error("不能识别的事件");
                            continue;
                        }
                        //获取config中事件对应的事件列表，出发他们的handle事件
                        for (EventHandler handler : config.get(eventModel.getType())) {
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }


}
