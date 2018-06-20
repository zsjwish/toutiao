package cn.iselab.mooctest.monitor.service;

import cn.iselab.mooctest.monitor.mysql.webIDE.dao.WebIDEUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by zsj in 20:22 2018/6/5
 * description:
 **/
@Service
public class WebIDEService {

    @Autowired
    WebIDEUserDAO webIDEUserDAO;

    public int getTotalWebIDEUser() {
        return webIDEUserDAO.getTotalWorkspace();
    }

    public int getOnlineNumbers() {
        return webIDEUserDAO.getOnlineWorkspace();
    }

    public int getDailyLoginWebIDE(String createdDate) {
        return webIDEUserDAO.getDailyLoginWebIDE(createdDate);
    }

}
