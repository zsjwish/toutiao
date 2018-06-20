package cn.iselab.mooctest.monitor.service;

import cn.iselab.mooctest.monitor.mysql.mainSite.dao.MonitorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by zsj in 17:23 2018/3/7
 **/
@Service
public class MonitorService {

    //获取用户增量
    private static int beforeTotalUserNumbers = 0;
    private static int afterTotalUserNumbers = 0;
    private static int brforeTotalCaseNumbers = 0;
    private static int afterTotalCaseNumbers = 0;
    private static String dateStart = "2018-03-13";
    private static String dateStartCase = "2018-03-15";

    @Autowired
    MonitorDao monitorDao;

    //用户数据
    public Integer getTotalUser() {
        return monitorDao.getTotalUser();
    }

    public Integer getAdminNumbers() {
        return monitorDao.getAdminNumbers();
    }

    public Integer getTeacherNumbers() {
        return monitorDao.getTeacherNumbers();
    }

    public Integer getStudentNumbers() {
        return monitorDao.getStudentNumbers();
    }

    public Integer getContestMentorNumbers() {
        return monitorDao.getContestMentorNumbers();
    }

    //考试数据
    public Integer getTotalExam() {
        return monitorDao.getTotalExam();
    }

    public Integer getUpComingExam() {
        return monitorDao.getUpComingExam();
    }

    public Integer getOnGoingExam() {
        return monitorDao.getOnGoingExam();
    }

    public Integer getFinishedExam() {
        return monitorDao.getFinishedExam();
    }

    //试卷数据
    public Integer getTotalCases() {
        return monitorDao.getTotalCases();
    }

    public Integer getPublicCaseNumbers() {
        return monitorDao.getPublicCaseNumbers();
    }

    public Integer getPersonalCaseNumbers() {
        return monitorDao.getPersonalCaseNumbers();
    }

    //注册用户日增
    public Integer getDailyIncrementUserNumbers() {
        String now = this.getDateNow();
        if (now.compareTo(dateStart) > 0) {
            beforeTotalUserNumbers = monitorDao.getTotalUser();
            dateStart = now;
        }
        afterTotalUserNumbers = monitorDao.getTotalUser();
        return afterTotalUserNumbers - beforeTotalUserNumbers;
    }

    //试题日增
    public Integer getDailyIncrementCaseNumbers() {
        String now = this.getDateNow();
        if (now.compareTo(dateStartCase) > 0) {
            brforeTotalCaseNumbers = monitorDao.getTotalCases();
            dateStartCase = now;
        }
        afterTotalCaseNumbers = monitorDao.getTotalCases();
        return afterTotalCaseNumbers - brforeTotalCaseNumbers;
    }

    public String getDateNow() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }


}
