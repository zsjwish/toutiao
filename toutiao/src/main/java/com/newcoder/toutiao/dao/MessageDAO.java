package com.newcoder.toutiao.dao;

import com.newcoder.toutiao.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * created by zsj in 15:27 2018/5/28
 * description:
 **/

@Mapper
public interface MessageDAO {
    /**
     * 操作数据库message表，
     * TABLE_NAME：表名
     * INSERT_FIELDS：插入字段，用于写sql语句时拼接，好处是更改字段时更改一处多处有用
     * SELECT_FIELDS：选择字段，同上
     */
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, created_date, has_read, conversation_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 增加一条站内信
     * @param message
     * @return
     */
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId})"})
    int addMessage(Message message);

    /**
     * 返回某个conversationId相关的所有站内信
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    @Select({"select ", SELECT_FIELDS, "from",TABLE_NAME,
            " where conversation_id=#{conversationId} order by id desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    /**
     * 和某个user相关的所有站内信。和一个用户是一条会话记录
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @Select({"select ",SELECT_FIELDS,", count(id) as id from (select ",SELECT_FIELDS," from ",TABLE_NAME,
            "where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id order by created_date limit #{offset},#{limit}" })
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    /**
     * 返回这个用户未读消息，to_id是自己id,这样才是发送给自己的未读，然后has_read应该是0，然后是根据某条具体的conversation_id 查询，
     * 这个conversation_id的意义是和某个人的所有未读消息总和。
     * @param userId
     * @param conversationId
     * @return
     */
    @Select({"select count(id) from ", TABLE_NAME, "where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}" })
    int getConversationUnreadCount(@Param("userId") int userId,
                                   @Param("conversationId") String conversationId);
}
