package cn.iselab.mooctest.monitor.controller;

import cn.iselab.mooctest.monitor.mysql.mainSite.models.Bug2CaseVO;
import cn.iselab.mooctest.monitor.service.Case2BugService;
import cn.iselab.mooctest.monitor.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zsj on 2018/2/23.
 */

@RestController
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private Case2BugService case2BugService;

//    @Autowired
//    private CaughtNodeService caughtNodeService;

    @RequestMapping(value = "/main/totalMembers", method = RequestMethod.GET)
    public Integer getTotalUser() {
        return monitorService.getTotalUser();
    }

    @RequestMapping(value = "/main/totalBugs", method = RequestMethod.GET)
    public Integer getTotalBugs() {
        return case2BugService.getTotalBugs();
    }

    @RequestMapping(value = "/main/bug2Case", method = RequestMethod.GET)
    public List<Bug2CaseVO> getBug2Case() {
        return case2BugService.getBugGroupByCase();
    }

    /**
     * 和session有关，走主站
     * @return
     */
//    @RequestMapping(value = "/main/dayLoginMembers", method = RequestMethod.GET)
//    public Integer getDayLoginSessions() {
//        return SessionCounter.getDayLoginSessions();
//    }

    @RequestMapping(value = "/main/adminNumbers", method = RequestMethod.GET)
    public Integer getAdminNumbers() {
        return monitorService.getAdminNumbers();
    }

    @RequestMapping(value = "/main/teacherNumbers", method = RequestMethod.GET)
    public Integer getTeacherNumbers() {
        return monitorService.getTeacherNumbers();
    }

    @RequestMapping(value = "/main/studentNumbers",method = RequestMethod.GET)
    public Integer getStudentNumbers() {
        return monitorService.getStudentNumbers();
    }

    @RequestMapping(value = "/main/contestMentorNumbers", method = RequestMethod.GET)
    public Integer getContestMentorNumbers() {
        return monitorService.getContestMentorNumbers();
    }

    @RequestMapping(value = "/main/totalExam", method = RequestMethod.GET)
    public Integer getTotalExam() {
        return monitorService.getTotalExam();
    }

    @RequestMapping(value = "/main/upComingExamNumbers", method = RequestMethod.GET)
    public Integer getUpComingExamNumbers() {
        return monitorService.getUpComingExam();
    }

    @RequestMapping(value = "/main/onGoingExamNumbers", method = RequestMethod.GET)
    public Integer getOnGoingExamNumbers() {
        return monitorService.getOnGoingExam();
    }

    @RequestMapping(value = "/main/finishedExamNumbers", method = RequestMethod.GET)
    public Integer getFinishedExamNumbers() {
        return monitorService.getFinishedExam();
    }

    @RequestMapping(value = "/main/totalCases", method = RequestMethod.GET)
    public Integer getTotalCases() {
        return monitorService.getTotalCases();
    }

    @RequestMapping(value = "/main/publicCaseNumbers", method = RequestMethod.GET)
    public Integer getPublicCaseNumbers() {
        return monitorService.getPublicCaseNumbers();
    }

    @RequestMapping(value = "/main/personalCaseNumbers", method = RequestMethod.GET)
    public Integer getPersonalCaseNumbers() {
        return monitorService.getPersonalCaseNumbers();
    }

    @RequestMapping(value = "/main/dailyIncrementUserNumbers", method = RequestMethod.GET)
    public Integer getDailyIncrementUserNumbers() {
        return monitorService.getDailyIncrementUserNumbers();
    }

    @RequestMapping(value = "/main/dailyIncrementCaseNumbers", method = RequestMethod.GET)
    public Integer getDailyIncrementCaseNumbers() {
        return monitorService.getDailyIncrementCaseNumbers();
    }

    /**
     * 从mongo数据库获取，走主站
     */
//    @RequestMapping(value = "/main/totalAnswerTimes", method = RequestMethod.GET)
//    public Long getTotalAnswerTimes() {
//        return caughtNodeService.getAllCaughtNodeNumbers();
//    }

}
