package com.newcoder.toutiao.dao;

import com.newcoder.toutiao.model.User;
import org.apache.ibatis.annotations.*;

/**
 * created by zsj in 19:00 2018/5/12
 * description:
 **/
@Mapper
public interface UserDAO {

    /**
     * 操作数据库user表，
     * TABLE_NAME：表名
     * INSERT_FIELDS：插入字段，用于写sql语句时拼接，好处是更改字段时更改一处多处有用
     * SELECT_FIELDS：选择字段，同上
     */
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url ";

    //增加用户，插入到数据库中
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") ",
            "values (#{name}, #{password}, #{salt}, #{headUrl})"})
    int addUser(User user);

    //通过用户id查询用户
    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, " where id=#{id}" })
    User selectById(int id);

    //通过用户姓名查询用户
    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    //通过用户id更新用户密码
    @Update({"update ", TABLE_NAME," set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    //通过用户id删除用户
    @Delete({"delete from ", TABLE_NAME, "where id=#{id}"})
    void deleteById(int id);
}
