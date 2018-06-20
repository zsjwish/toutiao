package com.newcoder.toutiao.service;

import com.newcoder.toutiao.dao.MessageDAO;
import com.newcoder.toutiao.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by zsj in 16:28 2018/5/28
 * description:
 **/
@Service
public class MessageService {

    @Autowired
    MessageDAO messageDAO;

    //增加一条message
    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    /**
     * 获取和当前用户有关的所有的会话，在DAO层判断的时候使用了userId,这个userId是在service层里从hostholder里面获取的
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    /**
     * 获取和某用户相关的所有会话，是以group by 的形式显示，所以一个会话是一条数据，在DAO层查找的时候
     * 只要from_id或者to_id和userId相同就被select出来
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDAO.getConversationList(userId, offset, limit);
    }

    /**
     * 选取to_id是userId并且未读的消息数量
     * @param userId
     * @param conversationId
     * @return
     */
    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDAO.getConversationUnreadCount(userId, conversationId);
    }
}
