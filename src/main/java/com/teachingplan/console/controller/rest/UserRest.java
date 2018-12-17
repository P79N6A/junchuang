package com.teachingplan.console.controller.rest;

import com.teachingplan.console.beans.user.Account;
import com.teachingplan.console.beans.user.Student;
import com.teachingplan.console.beans.user.User;
import com.teachingplan.console.common.CommonUtil;
import com.teachingplan.console.common.StringUtil;
import com.teachingplan.console.exception.BusinessException;
import com.teachingplan.console.service.impl.user.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by v_yanfzhang on 2018/2/12.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value="/rest", produces="application/json;charset=utf-8")
public class UserRest {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(UserRest.class);

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public Object login(String account,String password)
    {
        Map<String,String> returnMap = new HashMap<String,String>();

        if (StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)) {
            returnMap.put("returnCode", "0001");
            returnMap.put("returnMsg", "必填参数不能为空");
            return returnMap;
        }

        Account accountBean = userService.getAccountByAccount(account);

        if (null == accountBean || !accountBean.getPassword().equals(CommonUtil.toMD5String(password))) {
            returnMap.put("returnCode", "1001");
            returnMap.put("returnMsg", "用户名或密码错误");
            return returnMap;
        }else if("0".equals(accountBean.getStatus()) || "2".equals(accountBean.getStatus()) || "3".equals(accountBean.getStatus())) {
            returnMap.put("returnCode", "1002");
            returnMap.put("returnMsg", "账号已关闭");
            return returnMap;
        } else {
            returnMap.put("returnCode", "0");
            returnMap.put("returnMsg", "success");
        }

        return returnMap;
    }

    @RequestMapping(value = "/user/getCode",method = RequestMethod.GET)
    public Object getCode(String account)
    {
        Map<String,String> returnMap = new HashMap<String,String>();

        String code = userService.getCode(account);

        returnMap.put("code",code);
        returnMap.put("returnCode", "0");
        returnMap.put("returnMsg", "success");
        return returnMap;
    }

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public Object register(String account,String password,String name,Integer schoolId,String code)
    {
        Map<String,String> returnMap = new HashMap<String,String>();

        if (StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)
                || StringUtil.isNullOrEmpty(name)|| StringUtil.isNullOrEmpty(code) || null == schoolId) {
            returnMap.put("returnCode", "0001");
            returnMap.put("returnMsg", "必填参数不能为空");
            return returnMap;
        }

        try {
            userService.register(account, password, name, schoolId, code);

        } catch (BusinessException e) {
            returnMap.put("returnCode", e.getCode());
            returnMap.put("returnMsg", e.getMessage());
            return returnMap;
        }

        returnMap.put("code",code);
        returnMap.put("returnCode", "0");
        returnMap.put("returnMsg", "success");
        return returnMap;
    }

    @RequestMapping(value = "/user/resetPassword",method = RequestMethod.POST)
    public Object resetPassword(String account,String password,String code)
    {
        Map<String,String> returnMap = new HashMap<String,String>();

        if (StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)) {
            returnMap.put("returnCode", "0001");
            returnMap.put("returnMsg", "必填参数不能为空");
            return returnMap;
        }

        try {
            userService.resetPassword(account, password, code);
        } catch (BusinessException e) {
            returnMap.put("returnCode", e.getCode());
            returnMap.put("returnMsg", e.getMessage());
            return returnMap;
        }

        returnMap.put("returnCode", "0");
        returnMap.put("returnMsg", "success");
        return returnMap;
    }

    @RequestMapping(value = "/user/getTeacherMsg",method = RequestMethod.GET)
    public Object getTeacherMsg(String studentAccount,String code)
    {
        Map<String,String> returnMap = new HashMap<String,String>();

        if (StringUtil.isNullOrEmpty(studentAccount)) {
            returnMap.put("returnCode", "0001");
            returnMap.put("returnMsg", "必填参数不能为空");
            return returnMap;
        }

        String content = "";
        try {
            content = userService.getTeacherMsg(studentAccount);
        } catch (BusinessException e) {
            returnMap.put("returnCode", e.getCode());
            returnMap.put("returnMsg", e.getMessage());
            return returnMap;
        }

        returnMap.put("returnCode", "0");
        returnMap.put("returnMsg", "success");
        returnMap.put("content", null == content?"":content);
        return returnMap;
    }

    @RequestMapping(value = "/user/getAllSchool",method = RequestMethod.GET)
    public Object getAllSchool()
    {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        List<User> schools = userService.getAllSchools();

        List<String> schoolList = new ArrayList<>();
        for (User school : schools) {
            schoolList.add(school.getName());
        }

        returnMap.put("returnCode", "0");
        returnMap.put("returnMsg", "success");
        returnMap.put("schools", schools);
        return returnMap;
    }

    @RequestMapping(value = "/user/getVersion",method = RequestMethod.GET)
    public Object getVersion()
    {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        Map<String,String> versionMap = userService.getVersion();

        if (null == versionMap || versionMap.size() <= 0) {
            versionMap = new HashMap<>();
            versionMap.put("app_version","");
            versionMap.put("app_package","");
            versionMap.put("hardware_version","");
            versionMap.put("hardware_package","");
        } else {
            versionMap.remove("id");
        }

        returnMap.put("returnCode", "0");
        returnMap.put("returnMsg", "success");
        returnMap.put("returnData", versionMap);
        return returnMap;
    }
}
