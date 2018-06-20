package com.newcoder.toutiao.async;

import java.util.HashMap;
import java.util.Map;

/**
 * created by zsj in 15:06 2018/5/31
 * description:保存触发事件的信息,每个需要处理的时间都是一个EventModel实例。事件的数据
 **/
public class EventModel {

    //事件类型
    private EventType type;
    //事件触发者
    private int actorId;
    //触发对象
    private int entityType;
    private int entityId;
    //对象拥有者是谁，比如用户发信给用户，触发者是用户，拥有者也是用户
    private int entityOwnerId;
    //用来保存出发事件的参数信息
    private Map<String,String> exts = new HashMap<>();

    //构造函数
    public EventModel(EventType type) {
        this.type = type;
    }
    //构造函数
    public EventModel() {

    }

    //helper,获取map中某字段
    public String getExt(String key) {
        return exts.get(key);
    }
    //增加map中某字段
    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwneId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwneId(int entityOwneId) {
        this.entityOwnerId = entityOwneId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
