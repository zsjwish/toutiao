package com.newcoder.toutiao.service;

import com.newcoder.toutiao.util.JedisAdapter;
import com.newcoder.toutiao.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by zsj in 21:12 2018/5/29
 * description: 直接和redis交互，不和数据库交互，所以没有DAO，直接是service
 **/
@Service
public class LikeService {

    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 判断某个用户是否喜欢某个实体的对象，这里写的entity，是通用的，可以对不同的东西喜欢
     * 喜欢返回1，不喜欢返回-没操作返回0；
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDISLikeKey(entityId, entityType);
        return  jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;

    }

    /**
     * 返回有多少人喜欢某个实体，如果喜欢就把他从不喜欢集合中删除
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDISLikeKey(entityId, entityType);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

    /**
     * 返回有多少人喜欢某个实体，如果不喜欢就把他从喜欢集合中删除
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public long disLike(int userId, int entityType, int entityId) {
        String disLikeKey = RedisKeyUtil.getDISLikeKey(entityId, entityType);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        //获取是为了多线程的时候实时获取有多少人喜欢。
        return jedisAdapter.scard(likeKey);
    }
}
