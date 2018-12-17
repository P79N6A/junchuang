package com.teachingplan.console.controller.about;

import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.aboutUs.LabInfo;
import com.teachingplan.console.beans.user.User;
import com.teachingplan.console.service.impl.aboutUs.LabInfoService;
import com.teachingplan.console.service.impl.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LQW on 2017/11/4.
 */
@Controller
@RequestMapping("/public/aboutUs")
public class AboutUsController {

    @Autowired
    private LabInfoService labInfoService;

    @Autowired
    private UserService userService;

    @GetMapping("/lab")
    public String labInfo(Model model) {

        LabInfo lab = labInfoService.getLabInfo();
        model.addAttribute("lab", lab);
        return "aboutUs/labInfo";
    }

    @GetMapping("/teacher")
    public String teacherInfo(Model model, String type) {

        model.addAttribute("type", type);
        return "aboutUs/teacherInfo";
    }

    @GetMapping("/userInfo")
    @ResponseBody
    public Object searchUserInfo(Model model, String type, @RequestParam int rows, @RequestParam int page) {


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("beginIndex", (page - 1) * rows);
        params.put("pageSize", rows);
        params.put("type", type);
        int totalCount = userService.getUserTotalCount(params);
        int pageCount = totalCount / rows == 0 ? totalCount / rows : totalCount / rows + 1;
        List<User> userList = userService.getUserList(params);
        Map<String, Object> mapData = new HashMap<String, Object>();
        if (type.equals("1")) {
            mapData.put("tittle", "全职教师");
        } else if (type.equals("2")) {
            mapData.put("tittle", "兼职教师");
        }
//        else if (type.equals("3")){
//            mapData.put("tittle", "在读学生");
//        }else if (type.equals("4")){
//            mapData.put("tittle", "毕业学生");
//        }
        return new PagedSearchResult(page, totalCount, pageCount, userList, mapData);
    }

    @GetMapping("/student")
    public String searchStudentList(Model model, String type) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        List<User> userList = userService.getAllUserList(params);
        model.addAttribute("type", type);
        model.addAttribute("userList", userList);

        if (type.equals("3")) {
            model.addAttribute("tittle", "在读学生");
        } else if (type.equals("4")) {
            model.addAttribute("tittle", "毕业学生");
        }
        return "aboutUs/studentInfo";
    }

    @GetMapping("/studentInfo")
    @ResponseBody
    public Object getStudentInfo(Model model, String type, String id) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("id", id);
        List<User> userList = userService.getAllUserList(params);
        params.clear();
        params.put("user", userList.get(0));
        return params;
    }

    @GetMapping("/contactUs")
    public String contactUs() {
        return "aboutUs/contactUs";
    }

    public LabInfoService getLabInfoService() {
        return labInfoService;
    }

    public void setLabInfoService(LabInfoService labInfoService) {
        this.labInfoService = labInfoService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
