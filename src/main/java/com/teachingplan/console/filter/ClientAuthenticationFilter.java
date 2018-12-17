package com.teachingplan.console.filter;

import com.teachingplan.console.beans.user.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by v_yanfzhang on 2018/1/22.
 */
public class ClientAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        // 登录成功则放入session
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) subject.getPrincipal();
        session.setAttribute("account", user.getAccount());
        issueSuccessRedirect(request, response);
        return false;
    }
}
