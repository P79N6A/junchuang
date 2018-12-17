package com.teachingplan.console.controller.user;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.user.*;
import com.teachingplan.console.common.CommonContent;
import com.teachingplan.console.common.CommonUtil;
import com.teachingplan.console.common.StringUtil;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LQW on 2018/1/27.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(UserController.class);

    @GetMapping("/teacher/teacherList")
    public String teacherList(Model model) {

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
        return "user/teacherList";
    }

    @GetMapping("/student/studentList")
    public String studentList(Model model) {

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
        return "user/studentList";
    }

    @GetMapping("/teacher/searchData")
    @ResponseBody
    public Object searchTeacherData(Model model, String name, String classes, String schoolName, String status, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        Map<String,String> equalsParams = new HashMap<>();

        params.put("name",name);
        params.put("classes",classes);
        params.put("schoolName",schoolName);
        params.put("status",status);

        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");
        String accountType = accountBean.getType();

        String tableName = "teacher_v";
        // 学校用户，只查找自己的学校
        if ("1".equals(accountType)) {
            School school = userService.getSchoolByAccount(accountBean.getAccount());
            equalsParams.put("school_id",String.valueOf(school.getId()));
        }

        PagedSearchResult<Teacher> searchResult = userService.queryListWithEqualsCondition(tableName,page,rows,params,equalsParams,Teacher.class);
        return searchResult;
    }

    @PostMapping("/teacher/addTeacher")
    @ResponseBody
    public Object addTeacher(Teacher teacher, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        map.put("account",teacher.getAccount());
        map.put("name",teacher.getName());
        map.put("type","2");
        map.put("classes",teacher.getClasses());
        map.put("remark",teacher.getRemark());
        map.put("status","2");
        boolean isSuccss = userService.addAccount(map,String.valueOf(teacher.getSchoolId()));
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @PostMapping("/teacher/modTeacher")
    @ResponseBody
    public Object modTeacher(Teacher teacher, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = userService.modTeacher(teacher);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/teacher/delTeacher")
    @ResponseBody
    public Object delTeacher(Model model, int id){

        Map<String, String> map = new HashMap<>();
        userService.delTeacher(id);
        map.put("msg","success");
        return map;
    }

    @GetMapping("/student/searchData")
    @ResponseBody
    public Object searchStudentData(Model model, String name, String classes, String schoolName, String status, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        Map<String,String> equalsParams = new HashMap<>();

        params.put("name",name);
        params.put("classes",classes);
        params.put("schoolName",schoolName);
        params.put("status",status);

        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");
        String accountType = accountBean.getType();

        String tableName = "student_v";
        // 学校用户，只查找自己的学校
        if ("1".equals(accountType)) {

            School school = userService.getSchoolByAccount(accountBean.getAccount());
            equalsParams.put("school_id",String.valueOf(school.getId()));
        } else if ("2".equals(accountType)) {
            Map<String,Object> queryMap = new HashMap<>();

            queryMap.put("beginIndex",(page - 1) * rows);
            queryMap.put("pageSize",rows);
            queryMap.put("name",name);
            queryMap.put("classes",classes);
            queryMap.put("schoolName",schoolName);
            queryMap.put("status",status);
            queryMap.put("accountId",accountBean.getId());
            PagedSearchResult<Student> result = userService.getStuOfTeacher(queryMap,page,rows);
            return result;
        }

        PagedSearchResult<Student> searchResult = userService.queryListWithEqualsCondition(tableName,page,rows,params,equalsParams,Student.class);
        return searchResult;
    }

    @PostMapping("/student/addStudent")
    @ResponseBody
    public Object addStudent(Student student, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        map.put("account",student.getAccount());
        map.put("name",student.getName());
        map.put("type","3");
        map.put("classes",student.getClassesId());
        map.put("parentPhone",student.getParentPhone());
        map.put("birthday",student.getBirthday());
        // 通过学员管理添加的学员，状态为待审批
//        map.put("status","2");
        boolean isSuccss = userService.addAccount(map,String.valueOf(student.getSchoolId()));
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @PostMapping("/student/modStudent")
    @ResponseBody
    public Object modStudent(Student student, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        if (!StringUtil.isNullOrEmpty(student.getBirthday())) {
            String regex = "^\\d.*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(student.getBirthday());
            if (!matcher.find()) {
                student.setBirthday(null);
            }
        }
        boolean isSuccss = userService.modStudent(student);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/student/delStudent")
    @ResponseBody
    public Object delStudent(Model model, int id){

        Map<String, String> map = new HashMap<>();
        userService.delStudent(id);
        map.put("msg","success");
        return map;
    }

    @PostMapping("/student/signIn")
    @ResponseBody
    public Object signIn(int id, Model model) {

        Map<String, Object> map = new HashMap<>();
        userService.signIn(id);
        map.put("message","success");
        return map;
    }

    @GetMapping("/student/signInList")
    public String signInList(Model model) {

        return "user/signInList";
    }

    @GetMapping("/student/searchSignInData")
    @ResponseBody
    public Object searchSignInData(Model model, String name, String account,String beginDate, String endDate,String classes, String schoolName, @RequestParam int rows, @RequestParam int page){

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("name",name);
        params.put("classes",classes);
        params.put("schoolName",schoolName);
        params.put("account",account);
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);

        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");
        String accountType = accountBean.getType();

        String tableName = "student_v";
        // 学校用户，只查找自己的学校
        if ("1".equals(accountType)) {

            School school = userService.getSchoolByAccount(accountBean.getAccount());
            params.put("schoolId",String.valueOf(school.getId()));
        } else if ("2".equals(accountType)) {
            Map<String,Object> queryMap = new HashMap<>();
            params.put("teacherAccId",accountBean.getId());
        }

        PagedSearchResult<Sign> searchResult = userService.getSignInList(params,rows,page);
        return searchResult;
    }

    @PostMapping("/student/sendMessage")
    @ResponseBody
    public Object sendMessage(String studentIds,String content, Model model) {

        Map<String, Object> map = new HashMap<>();

        userService.sendMessage(studentIds,content);
        map.put("message","success");
        return map;
    }

    @PostMapping("/modPassword")
    @ResponseBody
    public Object modPassword(String account,String password, String newPassword) {

        Map<String, Object> map = new HashMap<>();

        Account accountBean = userService.findAccount(account);
        if (null == accountBean || !accountBean.getPassword().equals(CommonUtil.toMD5String(password))) {
            map.put("message","accountErr");
            return map;
        }
        userService.modPassword(account,newPassword);
        map.put("message","success");
        return map;
    }

    @GetMapping("/modPassword")
    public String modPasswordIndex() {
        return "user/modPassword";
    }

}
