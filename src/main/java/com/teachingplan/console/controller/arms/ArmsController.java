package com.teachingplan.console.controller.arms;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.arms.Ammunition;
import com.teachingplan.console.beans.arms.Arms;
import com.teachingplan.console.beans.device.Device;
import com.teachingplan.console.service.impl.arms.ArmsService;
import com.teachingplan.console.service.impl.device.DeviceService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/arms")
public class ArmsController {

    @Autowired
    private ArmsService armsService;

    private static final Logger logger = Logger.getLogger(ArmsController.class);

    @RequiresRoles("admin")
    @GetMapping("/armsList")
    public String armsList(Model model) {

        return "arms/arms";
    }

    @RequiresRoles("admin")
    @GetMapping("/ammunition/ammunitionList")
    public String ammunitionList(Model model) {

        return "arms/ammunition";
    }

    @GetMapping("/searchData")
    @ResponseBody
    public Object searchArmsData(Model model, Arms arms, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        params.put("deviceName",arms.getDeviceName());
        params.put("number",arms.getNumber());
        params.put("stationed_date",arms.getStationedDate());
        params.put("valid",arms.getValid());

        PagedSearchResult searchResult = armsService.queryListByPage("arms_t",page,rows,params,Arms.class);
        return searchResult;
    }

    @GetMapping("/ammunition/searchData")
    @ResponseBody
    public Object searchAmmunitionData(Model model, Ammunition ammunition, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        params.put("model",ammunition.getModel());
        params.put("name",ammunition.getName());

        PagedSearchResult searchResult = armsService.queryListByPage("ammunition_t",page,rows,params,Ammunition.class);
        return searchResult;
    }

    @RequiresRoles("admin")
    @PostMapping("/addArms")
    @ResponseBody
    public Object addArms(Arms arms, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        map.put("device_id",arms.getDeviceId());
        map.put("number",arms.getNumber());
        map.put("ranges",arms.getRanges());
        map.put("froms",arms.getFroms());
        map.put("check_date",arms.getCheckDate());
        map.put("check_user",arms.getCheckUser());
        map.put("check_result",arms.getCheckResult());
        map.put("belong_office",arms.getBelongOffice());
        map.put("license",arms.getLicense());
        map.put("camera_id",arms.getCameraId());
        map.put("camera_account",arms.getCameraAccount());
        map.put("camera_password",arms.getCameraPassword());
        map.put("valid",arms.getValid());
        map.put("stationed_date",new Date());

        InsertArgs insertArgs = new InsertArgs("arms_t",map);
        boolean isSuccess = armsService.addRecord(insertArgs);
        if (isSuccess) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @RequiresRoles("admin")
    @PostMapping("/ammunition/addAmmunition")
    @ResponseBody
    public Object addAmmunition(Ammunition ammunition, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        map.put("model",ammunition.getModel());
        map.put("name",ammunition.getName());
        map.put("delay",ammunition.getDelay());

        InsertArgs insertArgs = new InsertArgs("ammunition_t",map);
        boolean isSuccess = armsService.addRecord(insertArgs);
        if (isSuccess) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @PostMapping("/ammunition/modAmmunition")
    @ResponseBody
    public Object modAmmunition(Ammunition ammunition, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = armsService.modAmmunition(ammunition);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @PostMapping("/modArms")
    @ResponseBody
    public Object modArms(Arms arms, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = armsService.modArms(arms);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/ammunition/delAmmunition")
    @ResponseBody
    public Object delAmmunition(Model model, int id){

        Map<String, String> map = new HashMap<>();
        armsService.delAmmunition(id);
        map.put("msg","success");
        return map;
    }

    @GetMapping("/delArms")
    @ResponseBody
    public Object delDevice(Model model, int id){

        Map<String, String> map = new HashMap<>();
        armsService.delArms(id);
        map.put("msg","success");
        return map;
    }
}
