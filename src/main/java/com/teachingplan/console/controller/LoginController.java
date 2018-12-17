package com.teachingplan.console.controller;


import com.teachingplan.console.beans.menu.Menu;
import com.teachingplan.console.beans.user.*;
import com.teachingplan.console.beans.user.Account;
import com.teachingplan.console.common.CommonUtil;
import com.teachingplan.console.exception.ParameterException;
import com.teachingplan.console.service.impl.menu.MenuService;
import com.teachingplan.console.service.impl.user.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yanfzhang on 2017-06-03.
 */
@Controller
public class LoginController extends BaseController
{
    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @PostMapping("/login")
    @ResponseBody
    public Object login(@ModelAttribute User user)
    {
        Map<String,String> returnMap = new HashMap<String,String>();
        String userName = user.getAccount();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), CommonUtil.toMD5String(user.getPassword()));

        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + userName + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + userName + "]进行登录验证..验证通过");

        }catch(UnknownAccountException uae){
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,未知账户");
            returnMap.put("message", "未知账户");
        }catch(IncorrectCredentialsException ice){
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
            returnMap.put("message", "密码不正确");
        }catch(LockedAccountException lae){
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,账户未激活");
            returnMap.put("message", "账户未激活");
        }catch(ExcessiveAttemptsException eae){
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误次数过多");
            returnMap.put("message", "用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            returnMap.put("message", "用户名或密码不正确");
        }
        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            setLoginSuccess(user.getAccount());
            Account bean = userService.findAccount(user.getAccount());
            if ( 0==bean.getModDefault()) {
                currentUser.logout();
                returnMap.put("message", "modDefault");
            } else {
                returnMap.put("message", "success");
            }
        }
        return returnMap;
    }

    @GetMapping(value = "/logout")
    @ResponseBody
    public String logout() {

        Subject currentUser = SecurityUtils.getSubject();
        String result = "logout";
        currentUser.logout();
        return "index";
    }


    /**
     * 保存必要信息到缓存
     * @param account
     */
    private void setLoginSuccess(String account) {

        Session session = SecurityUtils.getSubject().getSession();

        Account bean = userService.findAccount(account);

        List<Menu> menus = menuService.getMenusByAccount(account);
        session.setAttribute("account", bean);
        session.setAttribute("menus", menus);

    }
}
