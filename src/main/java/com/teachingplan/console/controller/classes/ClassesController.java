package com.teachingplan.console.controller.classes;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.classes.Classes;
import com.teachingplan.console.beans.user.*;
import com.teachingplan.console.common.CommonContent;
import com.teachingplan.console.common.CommonUtil;
import com.teachingplan.console.common.StringUtil;
import com.teachingplan.console.exception.BusinessException;
import com.teachingplan.console.service.impl.classes.ClassesService;
import com.teachingplan.console.service.impl.user.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LQW on 2018/1/27.
 */
@Controller
@RequestMapping("/classes")
public class ClassesController {

    @Autowired
    private ClassesService classesService;

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(ClassesController.class);

    @GetMapping("/classesList")
    public String accountList(Model model) {

        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");
        String accountType = accountBean.getType();

        // 学校用户，只查找自己的学校
        if ("1".equals(accountType)) {
            School school = userService.getSchoolByAccount(accountBean.getAccount());
            List<School> schools = new ArrayList<>();
            schools.add(school);
            model.addAttribute("schools",schools);
        } else {
            List<User> schools = userService.getAllSchools();
            model.addAttribute("schools",schools);
        }
        return "classes/classesList";
    }

    @GetMapping("/searchData")
    @ResponseBody
    public Object searchData(Model model, String schoolName, String name, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        Map<String,String> equalsParams = new HashMap<>();
        params.put("schoolName",schoolName);
        params.put("name",name);

        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");
        String accountType = accountBean.getType();

        String tableName = "classes_v";
        // 学校用户，只查找自己的学校
        if ("1".equals(accountType)) {

            School school = userService.getSchoolByAccount(accountBean.getAccount());
            equalsParams.put("schoolId",String.valueOf(school.getId()));
        } else if ("2".equals(accountType)) {
            equalsParams.put("accountId",String.valueOf(accountBean.getId()));
            tableName = "teacher_classes_v";
        }

        PagedSearchResult<Classes> searchResult = classesService.queryListWithEqualsCondition(tableName,page,rows,params,equalsParams,Classes.class);
        return searchResult;
    }

    @GetMapping("/checkName")
    @ResponseBody
    public Object checkName(String name,int schoolId, Model model) {

        String message = "";
        Map<String, String> map = new HashMap<>();
        boolean isExist = classesService.checkNameExist(name,schoolId);
        if (isExist) {
            message="true";
        }
        map.put("isExist",message);
        return map;
    }

    @PostMapping("/addClasses")
    @ResponseBody
    public Object addClasses(Classes classes, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = classesService.addClasses(classes);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @PostMapping("/modClasses")
    @ResponseBody
    public Object modClasses(Classes classes, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = classesService.modClasses(classes);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/searchClasses")
    @ResponseBody
    public Object searchClasses(int schoolId, Model model) {

        Map<String, Object> map = new HashMap<>();
        List<Classes> classesList = classesService.getClassBySchoolId(schoolId);

        map.put("message","success");
        map.put("classes",classesList);
        return map;
    }

    @GetMapping("/delClasses")
    @ResponseBody
    public Object delClasses(Model model, int id){

        Map<String, String> map = new HashMap<>();
        boolean hasUser = classesService.hasUser(id);
        if(hasUser) {
            map.put("msg","hasUser");
            return map;
        }
        classesService.delClasses(id);
        map.put("msg","success");
        return map;
    }

    @GetMapping("/classesInfo")
    public String classesInfo(Model model,@RequestParam (value="classesId",required=false)Integer classesId) {
        Classes classes = new Classes();
        if (null != classesId) {
            classes = classesService.findClassesById(classesId);
        }
        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");
        String accountType = accountBean.getType();

        // 学校用户，只查找自己的学校
        if ("1".equals(accountType)) {
            School school = userService.getSchoolByAccount(accountBean.getAccount());
            List<School> schools = new ArrayList<>();
            schools.add(school);
            model.addAttribute("schools",schools);
        } else if("2".equals(accountType)) {
            Teacher teacher = userService.getTeacherByAccount(accountBean.getAccount());
            School school = userService.getSchoolById(teacher.getSchoolId());
            List<School> schools = new ArrayList<>();
            schools.add(school);
            model.addAttribute("schools",schools);
        } else {
            List<User> schools = userService.getAllSchools();
            model.addAttribute("schools",schools);
        }
        model.addAttribute("classes",classes);
        return "classes/classesInfo";
    }

    @GetMapping("/searchUserList")
    @ResponseBody
    public Object searchUserList(Model model, String classesId,String schoolId,String name,String type, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        params.put("name",name);
        params.put("type",type);
        Map<String,String> equalsParams = new HashMap<>();
        equalsParams.put("classesId",classesId);


        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");
        String accountType = accountBean.getType();
        // 学校用户，只查找自己的学校
        if ("1".equals(accountType)) {
            School school = userService.getSchoolByAccount(accountBean.getAccount());
            equalsParams.put("schoolId",String.valueOf(school.getId()));
        } else if("2".equals(accountType)) {
            Teacher teacher = userService.getTeacherByAccount(accountBean.getAccount());
            School school = userService.getSchoolById(teacher.getSchoolId());
            List<Integer> classesIds = new ArrayList<>();
            PagedSearchResult<Classes> classesResult = (PagedSearchResult<Classes>) searchData(null,school.getName(),"",1000,1);
            List<Classes> classesList = classesResult.getRows();
            if (null != classesList && classesList.size() > 0) {
                for (Classes classesBean:classesList) {
                    classesIds.add(classesBean.getId());
                }
            }
            if (classesIds.size() <=0) {
                classesIds.add(-1);
            } else {
                if (!StringUtil.isNullOrEmpty(classesId)) {
                    if (classesIds.contains(Integer.parseInt(classesId))) {
                        classesIds.clear();
                        classesIds.add(Integer.parseInt(classesId));
                    } else{
                        classesIds.clear();
                        classesIds.add(-1);
                    }
                }
            }
            Map<String,Object> includeParams = new HashMap<>();
            includeParams.put("name",name);
            includeParams.put("type",type);
            includeParams.put("schoolId",String.valueOf(school.getId()));
            includeParams.put("classesId",classesIds);
            PagedSearchResult<Student> searchResul = classesService.getClasses(includeParams,rows,page);
            return searchResul;
        } else {
            equalsParams.put("schoolId",schoolId);
        }

        PagedSearchResult<Student> searchResult = classesService.queryListWithEqualsCondition("classes_user_v",page,rows,params,equalsParams,Student.class);
        return searchResult;
    }

    @PostMapping("/addUser")
    @ResponseBody
    public Object addUser(int classesId,String type, String account, Model model) {

        String message = "faild";
        Map<String,String> map = new HashMap<>();
        Classes classes = classesService.findClassesById(classesId);
        if (null == classes) {
            logger.error("classes not exist");
            throw new BusinessException();
        }
        Account bean = userService.findAccount(account);
        if (null == bean) {
            map.put("message","accountError");
            return map;
        }
        if (!bean.getType().equals(type)) {
            map.put("message","typeError");
            return map;
        }
        int schoolId = 0;
        if ("3".equals(type)) {
            Student student = userService.getStudentByAccount(account);
            schoolId = student.getSchoolId();
        } else {
            Teacher teacher = userService.getTeacherByAccount(account);
            schoolId = teacher.getSchoolId();
        }
        if (schoolId != classes.getSchoolId()) {
            map.put("message","accountError");
            return map;
        }
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put("account_id",bean.getId());
        insertMap.put("classes_id",classesId);
        InsertArgs insertArgs = new InsertArgs("user_classes_t",insertMap);
        boolean isSuccss = userService.addRecord(insertArgs);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/delUser")
    @ResponseBody
    public Object delUser(Model model, int id,String account){

        Map<String,String> map = new HashMap<>();
        Account bean = userService.findAccount(account);

        classesService.userUnbindClasses(id,bean.getId());
        map.put("message","success");
        return map;
    }
}
