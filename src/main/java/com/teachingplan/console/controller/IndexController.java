package com.teachingplan.console.controller;

import com.teachingplan.console.beans.user.Account;
import com.teachingplan.console.beans.user.User;
import com.teachingplan.console.enums.ConfigEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yanfzhang on 2017-06-03.
 */
@Controller
public class IndexController {

    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String loginForm(Model model){
        Subject currentUser = SecurityUtils.getSubject();
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/index")
    public String index(Model model)
    {
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User) currentUser.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/")
    public String home(Model model)
    {
        String indexUrl = "login";
        Session session = SecurityUtils.getSubject().getSession();
        Account account = (Account) session.getAttribute("account");
        if (null != account) {
            String type = account.getType();
            switch(type) {
                case "0": // 管理员
                    indexUrl = "/account/accountList";
                    break;
                case "1": // 学校
                    indexUrl = "/user/teacher/teacherList";
                    break;
                case "2": // 教师
                    indexUrl = "/user/student/studentList";
                    break;
                default:
                    indexUrl = "login";
                    break;
            }

        }
        return "redirect:" + indexUrl;
    }
}
