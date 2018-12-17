package com.teachingplan.console.controller.file;

import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.category.Category;
import com.teachingplan.console.beans.file.FileDownload;
import com.teachingplan.console.beans.user.Account;
import com.teachingplan.console.beans.user.School;
import com.teachingplan.console.beans.user.User;
import com.teachingplan.console.service.impl.category.CategoryService;
import com.teachingplan.console.service.impl.file.FileDownloadService;
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
 * Created by v_yanfzhang on 2018/2/9.
 */
@Controller
@RequestMapping("/fileDownload")
public class FileDownloadController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileDownloadService fileDownloadService;

    @Autowired
    private TeachPlanService teachPlanService;

    private static final Logger logger = Logger.getLogger(FileDownloadController.class);

    @GetMapping("/fileDownloadList")
    public String fileDownloadList(Model model, String type) {


        List<Category> recruitStudents = categoryService.getCategoryBySubType("2","1");
        List<Category> teaching = categoryService.getCategoryBySubType("2","2");
        List<Category> management = categoryService.getCategoryBySubType("2","3");

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

        model.addAttribute("recruitStudents",recruitStudents);
        model.addAttribute("teachings",teaching);
        model.addAttribute("managements",management);

        return "file/fileDownloadList";
    }

    @GetMapping("/searchData")
    @ResponseBody
    public Object searchData(Model model, String name, String subTypeId, String type, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        String tableName = "";

        params.put("name",name);
        Map<String,String> equalsParams = new HashMap<>();
        equalsParams.put("subTypeId",subTypeId);
        equalsParams.put("type",type);

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
            tableName = "user_file_download_v";
        } else {
            tableName = "file_download_v";
        }

        PagedSearchResult<FileDownload> searchResult = fileDownloadService.queryListWithEqualsCondition(tableName,page,rows,params,equalsParams,FileDownload.class);
        return searchResult;
    }

    @PostMapping("/addFileDownload")
    @ResponseBody
    public Object addFileDownload(FileDownload fileDownload, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = fileDownloadService.addFileDownload(fileDownload);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @PostMapping("/allotFileDownload")
    @ResponseBody
    public Object allotFileDownload(Integer schoolId,String fileDownloads,String type, String account, Model model) {

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

        boolean isAllot = fileDownloadService.checkIsAllot(bean.getId(),fileDownloads);

        if (isAllot) {
            map.put("message","allortRrror");
            return map;
        }

        boolean isSuccss = fileDownloadService.allotFileDownload(fileDownloads,type,bean.getId());
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @PostMapping("/modFileDownload")
    @ResponseBody
    public Object modfileDownload(FileDownload fileDownload, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = fileDownloadService.modFileDownload(fileDownload);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/delFileDownload")
    @ResponseBody
    public Object delFileDownload(Model model, int id){

        Map<String, String> map = new HashMap<>();
        fileDownloadService.delFileDownload(id);
        map.put("msg","success");
        return map;
    }
}
