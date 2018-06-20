package com.newcoder.toutiao.dao;

import com.newcoder.toutiao.model.LoginTicket;
import org.apache.ibatis.annotations.*;


/**
 * created by zsj in 20:53 2018/5/17
 * description:
 **/
@Mapper
public interface LoginTicketDAO {
    /**
     * 操作数据库login_ticket表，
     * TABLE_NAME：表名
     * INSERT_FIELDS：插入字段，用于写sql语句时拼接，好处是更改字段时更改一处多处有用
     * SELECT_FIELDS：选择字段，同上
     */
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " user_id, ticket, expired, status ";

    //插入ticket，插入到数据库表中
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") ",
            "values (#{userId},#{ticket},#{expired},#{status})"})
    int addTicket(LoginTicket loginTicket);

    //通过ticket查询ticket
    @Select({"select", SELECT_FIELDS, "from ", TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    //通过ticket更新ticket状态。
    @Update({"update", TABLE_NAME," set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);



}
