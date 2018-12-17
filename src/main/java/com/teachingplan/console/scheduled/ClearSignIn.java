package com.teachingplan.console.scheduled;

import com.teachingplan.console.controller.file.FileController;
import com.teachingplan.console.service.impl.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by v_yanfzhang on 2018/2/11.
 */
@Component
public class ClearSignIn {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(ClearSignIn.class);


    @Scheduled(cron="0 0 0 * * ?")
    public void cron(){
        logger.info("================>>>>>>>>>清空签到标志");

        userService.clearSignIn();
    }
}
