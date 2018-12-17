package com.teachingplan.console.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yanfzhang on 2017-04-26.
 */
public abstract class BaseController
{
    /** 基于@ExceptionHandler异常处理 */
    @ExceptionHandler
    public ModelAndView exp(HttpServletRequest request, Exception ex)
    {

        ModelAndView modelAndView = new ModelAndView("error-business");
        String message = ex.getMessage();
        modelAndView.addObject("ex", message);
        // 根据不同错误转向不同页面
//        if (ex instanceof BusinessException)
//        {
//            modelAndView.setViewName("error-business.html");
//        }
//        else if (ex instanceof ParameterException)
//        {
//            modelAndView.setViewName("error-business.html");
//        }
//        else
//        {
//            modelAndView.setViewName("error.html");
//        }
        return modelAndView;
    }
}
