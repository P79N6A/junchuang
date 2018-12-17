package com.teachingplan.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 系统的启动类
 * Created by yanfzhang on 2017-06-03.
 */
@SpringBootApplication
@EnableScheduling
public class Application
{

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
