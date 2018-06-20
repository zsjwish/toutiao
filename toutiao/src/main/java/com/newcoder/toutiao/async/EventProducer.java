package com.newcoder.toutiao.async;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.toutiao.util.JedisAdapter;
import com.newcoder.toutiao.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by zsj in 15:32 2018/5/31
 * description:发送事件触发的数据，将事件放在队列中
 **/
@Service
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model) {
        try {
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
