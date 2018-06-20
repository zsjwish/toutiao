package com.newcoder.toutiao.dao;

import com.newcoder.toutiao.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * created by zsj in 11:56 2018/5/28
 * description:
 **/
@Mapper
public interface CommentDAO {
    /**
     * 操作数据库login_ticket表，
     * TABLE_NAME：表名
     * INSERT_FIELDS：插入字段，用于写sql语句时拼接，好处是更改字段时更改一处多处有用
     * SELECT_FIELDS：选择字段，同上
     */
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " content, user_id, entity_id, entity_type, created_date, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    //插入评论到数据库表中
    @Insert({"insert into " , TABLE_NAME , "(" ,INSERT_FIELDS , ") values " ,
            "(#{content}, #{userId},#{entityId},#{entityType},#{createdDate},#{status})"})
    //成功返回1，不成功返回0
    int addComment(Comment comment);

    //通过entityId 和entityType找到comment,返回列表
    @Select({"select ",SELECT_FIELDS," from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by id desc"})
    List<Comment> selectByEntity(@Param("entityId") int entityId,
                                 @Param("entityType") int entityType);

    //通过entityId 和entityType找到comment数量,返回列表数量
    @Select({"select count(id) from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId,
                        @Param("entityType") int entityType);

    //更新评论的状态，而不是删除
    @Update({"update ", TABLE_NAME, " set status=#{status} " +
            "where entity_id=#{entityId} and entity_type=#{entityType}"})
    void updateStatus(@Param("entityId") int entityId,
                      @Param("entityType") int entityType,
                      @Param("status") int status);





}
