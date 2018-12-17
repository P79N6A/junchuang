package com.teachingplan.console.controller.teachPlan;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.category.Category;
import com.teachingplan.console.beans.classes.Classes;
import com.teachingplan.console.beans.teachPlan.TeachPlan;
import com.teachingplan.console.beans.user.*;
import com.teachingplan.console.common.FileUtil;
import com.teachingplan.console.common.StringUtil;
import com.teachingplan.console.exception.BusinessException;
import com.teachingplan.console.service.impl.category.CategoryService;
import com.teachingplan.console.service.impl.classes.ClassesService;
import com.teachingplan.console.service.impl.teachPlan.TeachPlanService;
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
@RequestMapping("/teachPlan")
public class TeachPlanController {

    @Autowired
    private ClassesService classesService;

    @Autowired
    private TeachPlanService teachPlanService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(TeachPlanController.class);

    @GetMapping("/teachPlanList")
    public String accountList(Model model, String type) {

        List<Category> speCourses = null;
        List<Category> baseCourses = null;
        if ("2".equals(type)) {
            speCourses = categoryService.getCategoryBySubType("","2");
        } else if ("1".equals(type)){
            baseCourses = categoryService.getCategoryBySubType("","1");
        } else {
            speCourses = categoryService.getCategoryBySubType("","2");
            baseCourses = categoryService.getCategoryBySubType("","1");
            model.addAttribute("baseCourses",baseCourses);
            model.addAttribute("speCourses",speCourses);
            return "teachPlan/teachPlanIndex";
        }
        List<Category> scenes = categoryService.getCategoryBySubType("1","3");
        List<Category> themes = categoryService.getCategoryBySubType("1","4");

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

        model.addAttribute("baseCourses",baseCourses);
        model.addAttribute("speCourses",speCourses);
        model.addAttribute("scenes",scenes);
        model.addAttribute("themes",themes);

        return "teachPlan/teachPlanList";
    }

    @GetMapping("/addTeachPlan")
    public String addTeachPlan(Model model) {

        List<Category> scenes = categoryService.getCategoryBySubType("1","3");
        List<Category> themes = categoryService.getCategoryBySubType("1","4");
        model.addAttribute("scenes",scenes);
        model.addAttribute("themes",themes);
        return "teachPlan/addTeachPlan";
    }

    @GetMapping("/searchData")
    @ResponseBody
    public Object searchData(Model model, String type, String name,String courseId,String sceneId,String themeId, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        String tableName = "";

        params.put("name",name);
        Map<String,String> equalsParams = new HashMap<>();
        equalsParams.put("type",type);
        equalsParams.put("courseId",courseId);
        equalsParams.put("sceneId",sceneId);
        equalsParams.put("themeId",themeId);

        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");
        String accountType = accountBean.getType();

        if (!"0".equals(accountType)) {
            if ("1".equals(accountType)) {
                int accountId = userService.getSchoolByAccount(accountBean.getAccount()).getAccountId();
                equalsParams.put("accountId",String.valueOf(accountId));
            } else {
                equalsParams.put("accountId",String.valueOf(accountBean.getId()));
            }
            tableName = "user_teach_plan_v";
        } else {
            tableName = "teach_plan_v";
        }

        PagedSearchResult<TeachPlan> searchResult = classesService.queryListWithEqualsCondition(tableName,page,rows,params,equalsParams,TeachPlan.class);
        return searchResult;
    }

    @GetMapping("/detailList")
    public String detailList(Model model,String id) {

        if (!StringUtil.isNullOrEmpty(id)) {
            TeachPlan teachPlan = teachPlanService.getTeachPlanById(Integer.parseInt(id));
            if (null != teachPlan) {
                model.addAttribute("name",teachPlan.getName());
            }
        }
        model.addAttribute("id",id);
        return "teachPlan/teachPlanDetail";
    }

    @GetMapping("/searchDetailData")
    @ResponseBody
    public Object searchDetailData(Model model, String id, String name, @RequestParam int rows, @RequestParam int page){

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("name",name);
        params.put("id",id);

        Subject currentUser = SecurityUtils.getSubject();
        Account accountBean = (Account) currentUser.getSession().getAttribute("account");
        String accountType = accountBean.getType();

        if ("1".equals(accountType)) {
            params.put("accountType","2");
            School school = userService.getSchoolByAccount(accountBean.getAccount());
            params.put("schoolId",school.getId());
        } else {
            params.put("accountType","1");
        }

        PagedSearchResult<TeachPlan> searchResult = teachPlanService.getDetailList(params,rows,page);
        return searchResult;
    }

    @GetMapping("/searchCourse")
    @ResponseBody
    public Object searchCourse(Model model, String type){

        List<Category> courses = null;
        Map<String,Object> params = new HashMap<>();
        if ("2".equals(type)) {
            courses = categoryService.getCategoryBySubType("","2");
        } else if ("1".equals(type)){
            courses = categoryService.getCategoryBySubType("","1");
        } else {
            courses = new ArrayList<>();
        }
        params.put("courses",courses);
        params.put("message","success");
        return params;
    }

    @PostMapping("/addTeachPlan")
    @ResponseBody
    public Object addClasses(TeachPlan teachPlan, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = teachPlanService.addTeachPlan(teachPlan);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/teachPlanInfo")
    public String classesInfo(Model model,@RequestParam (value="teachPlanId",required=true)Integer teachPlanId) {

        TeachPlan teachPlan = teachPlanService.getTeachPlanById(teachPlanId);

        List<Category> courses = null;
        if ( 2 == teachPlan.getType()) {
            courses = categoryService.getCategoryBySubType("","2");
        } else {
            courses = categoryService.getCategoryBySubType("","1");
        }
        List<Category> scenes = categoryService.getCategoryBySubType("1","3");
        List<Category> themes = categoryService.getCategoryBySubType("1","4");

        String contentFilePath = teachPlan.getContent();
        teachPlan.setContent(FileUtil.fileRead(contentFilePath));

        String pptFilePath = teachPlan.getPptFilePath();
        String pptHtml = "";
        if (!StringUtil.isNullOrEmpty(pptFilePath)) {
            pptHtml = FileUtil.fileRead(pptFilePath.replace(pptFilePath.substring(pptFilePath.lastIndexOf("."), pptFilePath.length()),".html"));
        }

        model.addAttribute("teachPlan",teachPlan);
        model.addAttribute("contentFilePath",contentFilePath);
        model.addAttribute("pptHtml",pptHtml);
        model.addAttribute("courses",courses);
        model.addAttribute("scenes",scenes);
        model.addAttribute("themes",themes);
        return "teachPlan/teachPlanInfo";
    }

    @PostMapping("/modTeachPlan")
    @ResponseBody
    public Object modTeachPlan(TeachPlan teachPlan, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = teachPlanService.modTeachPlan(teachPlan);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/delTeachPlan")
    @ResponseBody
    public Object delTeachPlan(Model model, int id){

        Map<String, String> map = new HashMap<>();
        teachPlanService.delTeachPlan(id);
        map.put("msg","success");
        return map;
    }

    @PostMapping("/allotTeachPlan")
    @ResponseBody
    public Object allotTeachPlan(Integer schoolId,String teachPlanIds,String type, String account, Model model) {

        String message = "faild";
        Map<String,String> map = new HashMap<>();
        Account bean = userService.findAccount(account);
        if (type.equals("2")) {
            if (null == bean) {
                map.put("message","accountError");
                return map;
            }
            if (!bean.getType().equals(type)) {
                map.put("message","typeError");
                return map;
            }

            // 若分配的账号是教师账号，则判断账号是否是自己学校的
            boolean isMySchoolUser = teachPlanService.isMySchoolUser(schoolId,type,account);
            if (!isMySchoolUser) {
                map.put("message","schoolRrror");
                return map;
            }
        } else {
            School school = userService.getSchoolById(schoolId);
            bean = new Account();
            bean.setId(school.getAccountId());
        }

        boolean isAllot = teachPlanService.checkIsAllot(bean.getId(),teachPlanIds);

        if (isAllot) {
            map.put("message","allortRrror");
            return map;
        }

        boolean isSuccss = teachPlanService.allotTeachPlan(teachPlanIds,type,bean.getId());
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

}
