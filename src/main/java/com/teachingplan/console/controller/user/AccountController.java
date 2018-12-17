package com.teachingplan.console.controller.user;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.user.Account;
import com.teachingplan.console.beans.user.User;
import com.teachingplan.console.common.CommonContent;
import com.teachingplan.console.common.CommonUtil;
import com.teachingplan.console.service.impl.user.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by v_yanfzhang on 2018/1/23.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(AccountController.class);

    @RequiresRoles("admin")
    @GetMapping("/accountList")
    public String accountList(Model model) {

        List<User> schools = userService.getAllSchools();
        model.addAttribute("schools",schools);
        return "user/account";
    }

    @RequiresRoles("admin")
    @PostMapping("/addAccount")
    @ResponseBody
    public Object addAccount(Account account,String schoolId, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        map.put("account",account.getAccount());
        map.put("type",account.getType());
        map.put("name",account.getName());
        map.put("sex",account.getSex());
        map.put("phone",account.getPhone());
        map.put("office_phone",account.getOfficePhone());
        map.put("id_number",account.getIdNumber());
        map.put("join_renying_date",account.getJoinRenyingDate());
        map.put("province_approve_renying_date",account.getProvinceApproveRenyingDate());
        map.put("city_approve_renying_date",account.getCityApproveRenyingDate());
        map.put("address",account.getAddress());
        boolean isSuccess = userService.addAccount(map,schoolId);
        if (isSuccess) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/checkAccount")
    @ResponseBody
    public Object checkAccount(String account, Model model) {

        String message = "";
        Map<String, String> map = new HashMap<>();
        boolean isExist = userService.checkAccountExist(account);
        if (isExist) {
            message="true";
        }
        map.put("isExist",message);
        return map;
    }

    @GetMapping("/searchData")
    @ResponseBody
    public Object searchAccountData(Model model, Account account, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        params.put("account",account.getAccount());
        params.put("status",account.getStatus());
        params.put("type",account.getType());
        params.put("sex",account.getSex());
        params.put("name",account.getName());
        params.put("address",account.getAddress());

        Map<String,String> excludeParams = new HashMap<String,String>();
        // 不查询管理员账号
        excludeParams.put("type","0");
        PagedSearchResult searchResult = userService.queryListByPage("account_t",page,rows,params,excludeParams,Account.class);
        return searchResult;
    }

    @PostMapping("/modStatus")
    @ResponseBody
    public Object modStatus(String ids,int status, Model model) {

        Map<String, String> map = new HashMap<>();
        List<String> idList = Arrays.asList(ids.split(","));
        userService.modAccountStatus(idList,status);
        map.put("message","success");
        return map;
    }

    @GetMapping("/approveList")
    public String approveList(Model model) {

        List<User> schools = userService.getAllSchools();
        model.addAttribute("schools",schools);
        return "user/accountApprove";
    }

    @GetMapping("/searchApproveData")
    @ResponseBody
    public Object searchApproveData(Model model, String account, String schoolId, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        params.put("account",account);
        // 只查询待审批的账号
        params.put("status","2");
        params.put("school_id",schoolId);

        Map<String,String> excludeParams = new HashMap<String,String>();
        // 不查询管理员账号
        excludeParams.put("type","0");
        PagedSearchResult<User> searchResult = userService.queryListByPage("account_v",page,rows,params,excludeParams,User.class);
        return searchResult;
    }
}
