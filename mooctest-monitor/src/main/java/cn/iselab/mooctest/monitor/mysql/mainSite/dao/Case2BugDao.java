package cn.iselab.mooctest.monitor.mysql.mainSite.dao;

import cn.iselab.mooctest.monitor.mysql.mainSite.models.Bug;
import cn.iselab.mooctest.monitor.mysql.mainSite.models.Bug2CaseVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * created by zsj in 11:27 2018/2/27
 **/

@Transactional
public interface Case2BugDao extends CrudRepository<Bug, Integer> {

    @Query("SELECT COUNT(b) from Bug b")
    Integer getTotalBugs();

    @Query("SELECT c.id, c.name, COUNT(b.id) FROM CaseExtends c, Bug b, CaseTake ct " +
            "WHERE b.caseTakeId = ct.id " +
            "AND ct.caseId = c.id GROUP BY c.id")
    List<Bug2CaseVO> getBugGroupByCase();
}
