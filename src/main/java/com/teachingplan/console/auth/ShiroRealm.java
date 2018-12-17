package com.teachingplan.console.auth;

import com.teachingplan.console.beans.user.Account;
import com.teachingplan.console.beans.user.Permission;
import com.teachingplan.console.beans.user.Role;
import com.teachingplan.console.beans.user.User;
import com.teachingplan.console.controller.LoginController;
import com.teachingplan.console.dao.user.UserDao;
import com.teachingplan.console.exception.ParameterException;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取用户的角色和权限信息
 * Created by yanfzhang on 2017-10-31.
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = org.apache.log4j.Logger.getLogger(LoginController.class);

    //一般这里都写的是servic，我省略了service的接口和实现方法直接调用的dao
    @Autowired
    private UserDao userDao;

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + token.toString());
        //查出是否有此用户
        Account user = null;
        try{
            user = userDao.findAccount(token.getUsername());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (user != null) {

            // 已关闭的账号不能登录
            if("0".equals(user.getStatus()) || "2".equals(user.getStatus()) || "3".equals(user.getStatus())) {
                throw new LockedAccountException();
            }
            // 学员账号不能登录
            if ("3".equals(user.getType())) {
                throw new UnknownAccountException();
            }

            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            List<Role> rlist = userDao.findRolesByAccountId(user.getId());//获取用户角色
            List<Permission> plist = userDao.findPermissionsByAccountId(user.getId());//获取用户权限
            List<String> roleStrlist=new ArrayList<String>();////用户的角色集合
            List<String> perminsStrlist=new ArrayList<String>();//用户的权限集合
            for (Role role : rlist) {
                roleStrlist.add(role.getName());
            }
            for (Permission permission : plist) {
                perminsStrlist.add(permission.getName());
            }
            user.setRoleList(roleStrlist);
            user.setPermissionList(perminsStrlist);
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }
        return null;
    }

    /**
     * 权限认证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
//        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User userInfo  = (User)principalCollection.getPrimaryPrincipal();
//        //到数据库查是否有此对象
//        User user = null;// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        user = userMapper.findByName(loginName);
        if (userInfo != null) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //用户的角色集合
//            info.addRoles(user.getRoleList());
//            //用户的权限集合
//            info.addStringPermissions(user.getPermissionList());

            for(String role : userInfo.getRoleList()){
                authorizationInfo.addRole(role);
            }
            for (String permission : userInfo.getPermissionList()) {
                authorizationInfo.addStringPermission(permission);
            }
            return authorizationInfo;

        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

}