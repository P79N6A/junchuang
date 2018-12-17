package com.teachingplan.console.controller.device;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.device.Device;
import com.teachingplan.console.service.impl.device.DeviceService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    private static final Logger logger = Logger.getLogger(DeviceController.class);

    @RequiresRoles("admin")
    @GetMapping("/deviceList")
    public String accountList(Model model) {

        return "device/device";
    }

    @RequiresRoles("admin")
    @GetMapping("/map")
    public String map(Model model) {

        return "device/map";
    }

    @GetMapping("/searchData")
    @ResponseBody
    public Object searchAccountData(Model model, Device device, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        params.put("name",device.getName());
        params.put("department",device.getDepartment());
        params.put("airspace",device.getAirspace());

        PagedSearchResult searchResult = deviceService.queryListByPage("device_t",page,rows,params,Device.class);
        return searchResult;
    }

    @RequiresRoles("admin")
    @PostMapping("/addDevice")
    @ResponseBody
    public Object addAccount(Device device,String schoolId, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        map.put("name",device.getName());
        map.put("department",device.getDepartment());
        map.put("latitude",device.getLatitude());
        map.put("longitude",device.getLongitude());
        map.put("height",device.getHeight());
        map.put("ip",device.getIp());
        map.put("port",device.getPort());
        map.put("camera_id",device.getCameraId());
        map.put("camera_account",device.getCameraAccount());
        map.put("camera_password",device.getCameraPassword());
        map.put("airspace",device.getAirspace());

        InsertArgs insertArgs = new InsertArgs("device_t",map);
        boolean isSuccess = deviceService.addRecord(insertArgs);
        if (isSuccess) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @PostMapping("/modDevice")
    @ResponseBody
    public Object modDevice(Device device, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccss = deviceService.modDevice(device);
        if (isSuccss) {
            message="success";
        }
        map.put("message",message);
        return map;
    }

    @GetMapping("/delDevice")
    @ResponseBody
    public Object delDevice(Model model, int id){

        Map<String, String> map = new HashMap<>();
        deviceService.delDevice(id);
        map.put("msg","success");
        return map;
    }
}
