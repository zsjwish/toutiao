package cn.iselab.mooctest.monitor.mysql.webIDE.dao;

import cn.iselab.mooctest.monitor.mysql.webIDE.models.User;
import cn.iselab.mooctest.monitor.mysql.webIDE.models.WorkspaceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * created by zsj in 22:40 2018/6/2
 * description:
 **/
@Transactional
public interface WebIDEUserDAO extends CrudRepository<WorkspaceEntity, Long> {

    //做题总数
    @Query("select COUNT(w) from WorkspaceEntity w")
    Integer getTotalWorkspace();

    //当前WEBIDE在线人数
    @Query("select count(w) from WorkspaceEntity w where f_working_status='Online'")
    Integer getOnlineWorkspace();

    //今天登陆WEBIDE用户数
    @Query("select count(w) from WorkspaceEntity w where f_created_at >= :createdDate")
    Integer getDailyLoginWebIDE(@Param("createdDate") String createDate);


}
