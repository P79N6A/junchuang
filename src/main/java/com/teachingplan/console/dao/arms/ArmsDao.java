package com.teachingplan.console.dao.arms;

import com.teachingplan.console.beans.arms.Ammunition;
import com.teachingplan.console.beans.arms.Arms;

public interface ArmsDao {
    void modArms(Arms arms);

    void delArms(int id);

    void modAmmunition(Ammunition ammunition);

    void delAmmunition(int id);
}
