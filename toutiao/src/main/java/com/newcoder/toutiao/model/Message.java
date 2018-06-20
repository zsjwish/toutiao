package com.newcoder.toutiao.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * created by zsj in 15:25 2018/5/28
 * description:
 **/
@Getter
@Setter
public class Message {
    private int id;
    //from_id表示发信人id，to_id表示收信人id
    private int fromId;
    private int toId;
    private String content;
    private Date createdDate;
    //用来判断是否已经读过，如果没有读过为0，读过为1
    private int hasRead;
    private String conversationId;

    //在需要conversationId的时候在这个get，因为他是根据from_id 和to_id拼接的，所以直接在get的时候获取好返回
    public String getConversationId() {
        if (fromId < toId) {
            return String.format("%d_%d", fromId, toId);
        }
        return String.format("%d_%d", toId, fromId);
    }
}
