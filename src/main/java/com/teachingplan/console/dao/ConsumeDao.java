package com.teachingplan.console.dao;

import com.teachingplan.console.beans.ConsumeBean;

import java.util.List;
import java.util.Map;

/**
 * Created by yanfzhang on 2017-06-03.
 */
public interface ConsumeDao {

    List<ConsumeBean> queryConsumeList(Map params);

    void addConsume(ConsumeBean consumeBean);

    int getTotalCount(Map<String, Object> params);

    void modConsume(ConsumeBean consumeBean);

    void delConsumeById(int id);

    double getTotalMoney(Map<String, Object> params);
}
