package com.newcoder.toutiao.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于传递给前端展示使用。传递的是一个map。
 */
public class ViewObject {
    private Map<String, Object> objs = new HashMap<String, Object>();
    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
