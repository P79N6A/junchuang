package com.teachingplan.console.service.impl;

import com.teachingplan.console.beans.ConsumeBean;
import com.teachingplan.console.dao.ConsumeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yanfzhang on 2017-06-03.
 */
@Service("consumeService")
public class ConsumeService {

    @Autowired
    private ConsumeDao consumeDao;

    public List<ConsumeBean> getConsumeList(Map params)
    {
        String type = (String) params.get("type");
        return consumeDao.queryConsumeList(params);
    }

    public void addConsume(ConsumeBean consumeBean) {
        consumeBean.setUpdateDate(new Date());
        if(consumeBean.getType().equals("0"))
        {
            consumeBean.setExpenseType("");
        }
        else if (consumeBean.getType().equals("1"))
        {
            consumeBean.setConsumeType("");
        }
        consumeDao.addConsume(consumeBean);
    }

    public void modConsume(ConsumeBean consumeBean) {
        consumeBean.setUpdateDate(new Date());
        if(consumeBean.getType().equals("0"))
        {
            consumeBean.setExpenseType("");
        }
        else if (consumeBean.getType().equals("1"))
        {
            consumeBean.setConsumeType("");
        }
        consumeDao.modConsume(consumeBean);
    }

    public void delConsumeById(int id) {
        consumeDao.delConsumeById(id);
    }

    public double getTotalMoney(Map<String, Object> params) {
        return consumeDao.getTotalMoney(params);
    }

    public int getTotalCount(Map<String, Object> params) {
        return  consumeDao.getTotalCount(params);
    }

    public ConsumeDao getConsumeDao() {
        return consumeDao;
    }

    public void setConsumeDaoDao(ConsumeDao consumeDao) {
        this.consumeDao = consumeDao;
    }
}
