package com.teachingplan.console.controller.category;

import com.alibaba.fastjson.JSONArray;
import com.teachingplan.console.beans.category.Category;
import com.teachingplan.console.beans.classes.Classes;
import com.teachingplan.console.service.impl.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v_yanfzhang on 2018/1/31.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/teachPlan")
    public String teachPlanList(Model model) {

        model.addAttribute("type",1);
        model.addAttribute("title","教案类别列表");
        return "category/categoryList";
    }

    @GetMapping("/file")
    public String fileList(Model model) {

        model.addAttribute("type",2);
        model.addAttribute("title","文件类别列表");
        return "category/categoryList";
    }

    @GetMapping("/getCategoryList")
    @ResponseBody
    public Object searchData(Model model,int type){

        List<Category> categoryList = categoryService.getCategoryByType(type);

        System.out.println(categoryList.toString());
//        String jsonString = JSONArray.toJSONString(categoryList);
//        return jsonString.replace("{\"","{").replace("\":",":").replace(",\"",",");
        return categoryList;
    }

    @PostMapping("/addCategory")
    @ResponseBody
    public Object addCategory(Category category, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isExist = categoryService.checkNameIsExist(category.getText(),category.getParentId());
        if (isExist) {
            map.put("message",message);
            return map;
        }
        categoryService.addCategory(category);
        Category bean = categoryService.findCategory(category.getText(),category.getParentId());
        map.put("message","success");
        map.put("category",bean);
        return map;
    }
    @PostMapping("/modCategory")
    @ResponseBody
    public Object modCategory(Category category, Model model) {

        Map<String, Object> map = new HashMap<>();
        categoryService.modCategory(category);
        map.put("message","success");
        map.put("isMod","true");
        return map;
    }

    @GetMapping("/delCategory")
    @ResponseBody
    public Object delCategory(Model model, int id){

        Map<String, String> map = new HashMap<>();
        boolean hasChild = categoryService.hasChildOrPlan(id);
        if(hasChild) {
            map.put("msg","hasChild");
            return map;
        }
        categoryService.delCategory(id);
        map.put("msg","success");
        return map;
    }

    @GetMapping("/searchCategory")
    @ResponseBody
    public Object searchCategory(String type,String subType, Model model) {

        Map<String, Object> map = new HashMap<>();
        List<Category> categorys = categoryService.getCategoryBySubType(type,subType);

        map.put("message","success");
        map.put("categorys",categorys);
        return map;
    }
}
