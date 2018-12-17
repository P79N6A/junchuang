package com.teachingplan.console.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.teachingplan.console.auth.ShiroRealm;
import com.teachingplan.console.filter.ClientAuthenticationFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by LQW on 2017/10/31.
 */
@Configuration
public class ShiroConfiguration {

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
    //指定加密方式方式，也可以在这里加入缓存，当用户超过五次登陆错误就锁定该用户禁止不断尝试登陆
//    @Bean(name = "hashedCredentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        credentialsMatcher.setHashIterations(2);
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return credentialsMatcher;
//    }

    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm() {
        ShiroRealm realm = new ShiroRealm();
//        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    @Bean(name = "ehCacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        return ehCacheManager;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(ehCacheManager());//用户授权/认证信息Cache, 采用EhCache 缓存
        return securityManager;
    }

    @Bean(name="sessionManager")
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false); // 去除地址栏中的
        return sessionManager;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager  securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

//        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
//        LogoutFilter logoutFilter = new LogoutFilter();
//        logoutFilter.setRedirectUrl("/login");
//        filters.put("logout", logoutFilter);
//        shiroFilterFactoryBean.setFilters(filters);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<>();
        filterChainDefinitionManager.put("/logout", "logout");
        filterChainDefinitionManager.put("/public/**", "anon");//anon 可以理解为不拦截
        filterChainDefinitionManager.put("/login", "anon");//anon 可以理解为不拦截
        filterChainDefinitionManager.put("/index", "anon");//anon 可以理解为不拦截
        filterChainDefinitionManager.put("/css/**",  "anon");//静态资源不拦截
        filterChainDefinitionManager.put("/fonts/**",  "anon");//静态资源不拦截
        filterChainDefinitionManager.put("/js/**",  "anon");//静态资源不拦截
        filterChainDefinitionManager.put("/image/**",  "anon");//静态资源不拦截
        filterChainDefinitionManager.put("/file/**",  "anon");//文件资源不拦截
        filterChainDefinitionManager.put("/rest/**",  "anon");//接口不拦截
        filterChainDefinitionManager.put("/error-business",  "anon");//静态资源不拦截
        filterChainDefinitionManager.put("/user/modPassword",  "anon");//修改密码不拦截
        filterChainDefinitionManager.put("/**",  "authc");//其他资源全部拦截
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error-business");

        return shiroFilterFactoryBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    //thymeleaf模板引擎和shiro整合时使用
    //todo
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
