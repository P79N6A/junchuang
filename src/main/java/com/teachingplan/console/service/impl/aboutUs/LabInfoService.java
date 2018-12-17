package com.teachingplan.console.service.impl.aboutUs;

import com.teachingplan.console.beans.aboutUs.LabInfo;
import com.teachingplan.console.dao.aboutUs.AboutUsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LQW on 2017/11/4.
 */
@Service("labInfoService")
public class LabInfoService {

    @Autowired
    private AboutUsDao aboutUsDao;
    public LabInfo getLabInfo(){
        return aboutUsDao.getLabInfo();
    }

    public AboutUsDao getAboutUsDao() {
        return aboutUsDao;
    }

    public void setAboutUsDao(AboutUsDao aboutUsDao) {
        this.aboutUsDao = aboutUsDao;
    }
}
