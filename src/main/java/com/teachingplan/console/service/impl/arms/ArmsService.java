package com.teachingplan.console.service.impl.arms;

import com.teachingplan.console.beans.arms.Ammunition;
import com.teachingplan.console.beans.arms.Arms;
import com.teachingplan.console.dao.arms.ArmsDao;
import com.teachingplan.console.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArmsService extends BaseService {

    @Autowired
    private ArmsDao armsDao;

    public boolean modArms(Arms arms) {
        armsDao.modArms(arms);
        return true;
    }

    public void delArms(int id) {
        armsDao.delArms(id);
    }

    public boolean modAmmunition(Ammunition ammunition) {
        armsDao.modAmmunition(ammunition);
        return true;
    }

    public void delAmmunition(int id) {
        armsDao.delAmmunition(id);
    }
}
