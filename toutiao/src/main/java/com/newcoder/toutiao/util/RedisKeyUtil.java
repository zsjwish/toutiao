package com.newcoder.toutiao.util;

/**
 * created by zsj in 21:17 2018/5/29
 * description: 规范redis的key 的命名。防止在增加key的时候重复
 **/
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT = "EVENT";
    /**
     * 某个用户对某个实体类型下的某个具体实体喜欢
     * @param entityId
     * @param entityType
     * @return
     */
    public static String getLikeKey(int entityId, int entityType) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + String.valueOf(entityId);
    }

    /**
     * 某个用户对某个实体类型下的某个具体实体不喜欢
     * @param entityId
     * @param entityType
     * @return
     */
    public static String getDISLikeKey(int entityId, int entityType) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENT;
    }

}
