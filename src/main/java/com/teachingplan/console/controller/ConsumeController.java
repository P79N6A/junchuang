package com.teachingplan.console.controller;

import com.teachingplan.console.beans.ConsumeBean;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.service.impl.ConsumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by v_yanfzhang on 2017/6/5.
 */
@Controller
@RequestMapping("/consume")
public class ConsumeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ConsumeController.class);
    @Autowired
    private ConsumeService consumeService;

    @RequestMapping("/query")
    @ResponseBody
    public Object getConsumeLists(@RequestParam Map<String, Object> params,@RequestParam int rows, @RequestParam int page,Model model)
    {
        Map<String, Object> mapData = new HashMap<String,Object>();

        params.put("beginIndex",(page -1) * rows );
        params.put("pageSize",rows);
        List<ConsumeBean> consumeBeanList =  consumeService.getConsumeList(params);
        int totalCount = consumeService.getTotalCount(params);
        int pageCount = totalCount/rows == 0? totalCount/rows : totalCount/rows + 1;

        params.put("type","0");
        Double consumeMoney = consumeService.getTotalMoney(params);
        params.put("type","1");
        Double expenseMoney = consumeService.getTotalMoney(params);
        double netIncome = expenseMoney - consumeMoney;
        mapData.put("consumeMoney", consumeMoney);
        mapData.put("expenseMoney", expenseMoney);
        mapData.put("netIncome", netIncome);
        return new PagedSearchResult(page, totalCount, pageCount, consumeBeanList,mapData);
    }

    @PostMapping("/add")
    @ResponseBody
    public Object addConsume(ConsumeBean consumeBean,Model model)
    {
        Map<String, String> map = new HashMap<>();
        consumeService.addConsume(consumeBean);
        map.put("msg","success");
        return map;
    }

    @PostMapping("/mod")
    @ResponseBody
    public Object modConsume(ConsumeBean consumeBean,Model model)
    {
        Map<String, String> map = new HashMap<>();
        consumeService.modConsume(consumeBean);
        map.put("msg","success");
        return map;
    }

    @RequestMapping("/del")
    @ResponseBody
    public Object delConsume(@RequestParam int id)
    {
        Map<String, String> map = new HashMap<>();
        consumeService.delConsumeById(id);
        map.put("msg","success");
        return map;
    }

    public ConsumeService getConsumeService() {
        return consumeService;
    }

    public void setConsumeService(ConsumeService consumeService) {
        this.consumeService = consumeService;
    }
}
