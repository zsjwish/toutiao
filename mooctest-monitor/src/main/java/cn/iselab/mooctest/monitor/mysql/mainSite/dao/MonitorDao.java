package cn.iselab.mooctest.monitor.mysql.mainSite.dao;


import cn.iselab.mooctest.monitor.mysql.mainSite.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by zsj on 2018/2/23.
 */

@Transactional
public interface MonitorDao extends CrudRepository<User, Long>{

    @Query("SELECT COUNT(u) FROM User u")
    Integer getTotalUser();

    @Query("SELECT COUNT(DISTINCT u2r.userId) FROM User2Role u2r, User u WHERE u2r.roleId = 1 AND u2r.userId = u.id")
    Integer getAdminNumbers();

    @Query("SELECT COUNT(DISTINCT u2r.userId) FROM User2Role u2r, User u WHERE u2r.roleId = 2 AND u2r.userId = u.id")
    Integer getTeacherNumbers();

    @Query("SELECT COUNT(DISTINCT u2r.userId) FROM User2Role u2r, User u WHERE u2r.roleId = 3 AND u2r.userId = u.id")
    Integer getStudentNumbers();

    @Query("SELECT COUNT(DISTINCT u2r.userId) FROM User2Role u2r, User u WHERE u2r.roleId = 4 AND u2r.userId = u.id")
    Integer getContestMentorNumbers();

    @Query("SELECT COUNT(t) FROM Exam t")
    Integer getTotalExam();

    @Query("SELECT COUNT(t) FROM Exam t WHERE t.status = 0 AND t.beginTime < t.endTime")
    Integer getUpComingExam();

    @Query("SELECT COUNT(t) FROM Exam t WHERE t.status = 1 AND t.beginTime < t.endTime")
    Integer getOnGoingExam();

    @Query("SELECT COUNT(t) FROM Exam t WHERE (t.status = 2 OR t.status = 3 )AND t.beginTime < t.endTime")
    Integer getFinishedExam();

    @Query("SELECT COUNT(c) FROM CaseExtends c WHERE c.deleted = false")
    Integer getTotalCases();

    @Query("SELECT COUNT(c) FROM CaseExtends c WHERE c.deleted = false AND c.visible = true ")
    Integer getPublicCaseNumbers();

    @Query("SELECT COUNT(c) FROM CaseExtends c WHERE c.deleted = false AND c.visible = false ")
    Integer getPersonalCaseNumbers();

}
