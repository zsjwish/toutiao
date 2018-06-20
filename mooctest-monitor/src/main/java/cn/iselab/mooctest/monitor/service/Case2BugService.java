package cn.iselab.mooctest.monitor.service;

import cn.iselab.mooctest.monitor.mysql.mainSite.dao.Case2BugDao;
import cn.iselab.mooctest.monitor.mysql.mainSite.models.Bug2CaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by zsj in 16:21 2018/3/7
 **/
@Service
public class Case2BugService {

    @Autowired
    Case2BugDao case2BugDao;

    public int getTotalBugs() {
        return case2BugDao.getTotalBugs();
    }

    public List<Bug2CaseVO> getBugGroupByCase() {
        return case2BugDao.getBugGroupByCase();
    }
}
