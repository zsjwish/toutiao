package com.newcoder.toutiao.dao;

import com.newcoder.toutiao.model.News;
import javafx.scene.control.Tab;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * created by zsj in 21:11 2018/5/12
 * description:
 **/
@Mapper
public interface NewsDAO {
    /**
     * 操作数据库news表，
     * TABLE_NAME：表名
     * INSERT_FIELDS：插入字段，用于写sql语句时拼接，好处是更改字段时更改一处多处有用
     * SELECT_FIELDS：选择字段，同上
     */
    String TABLE_NAME = "news";
    String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id ";
    String SELECT_FIELDS = " id, title, link, image, like_count, comment_count, created_date, user_id ";

    //增加news,插入到数据库中
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") ",
            "values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    //通过news的id查询news
    @Select({"select", SELECT_FIELDS, "from ", TABLE_NAME, " where id=#{id}"})
    News getById(int id);

    //通过news的标题查询news
    @Select({"select", SELECT_FIELDS, "from ", TABLE_NAME, " where title=#{title}"})
    News getByTitle(String title);

    //通过news的id更新新闻的评论数量
    @Update({"update", TABLE_NAME," set comment_count=#{commentCount} where id=#{id}"})
    int updateCommentCountById(@Param("id") int id, @Param("commentCount") int commentCount);

    //通过news的id更新新闻点赞数
    @Update({"update", TABLE_NAME," set like_count=#{likeCount} where id=#{id}"})
    int updateLikeCountById(@Param("id") int id, @Param("likeCount") int likeCount);

    List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
                                       @Param("limit") int limit);



}
