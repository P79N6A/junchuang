package com.teachingplan.console.dao.teachPlan;

import com.teachingplan.console.beans.classes.Classes;
import com.teachingplan.console.beans.teachPlan.TeachPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by LQW on 2018/1/27.
 */
public interface TeachPlanDao {

    TeachPlan getTeachPlanById(int teachPlanId);

    void modTeachPlan(TeachPlan teachPlan);

    void delTeachPlan(int id);

    int getTeachPlanCountOfUser(@Param("accountId") int accountId,@Param("teachPlanId") String teachPlanId);

    int getDetailCount(Map<String, Object> params);

    List<TeachPlan> getDetails(Map<String, Object> params);
}
