package com.teachingplan.console.controller.operation;

import com.teachingplan.console.beans.InsertArgs;
import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.device.Device;
import com.teachingplan.console.beans.operation.Operation;
import com.teachingplan.console.service.impl.operation.OperationService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @RequiresRoles("admin")
    @GetMapping("/plan")
    public String operationPlan(Model model) {

        return "operation/plan";
    }

    @RequiresRoles("admin")
    @GetMapping("/data")
    public String operationData(Model model) {

        return "operation/data";
    }

    @RequiresRoles("admin")
    @PostMapping("/create")
    @ResponseBody
    public Object addAccount(String latitude, String longitude, String height, Model model) {

        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccess = true;

        List<Operation> operations = new ArrayList<>();
        Operation operation = new Operation();
        operation.setId(1);
        operation.setDeviceName("aaa");
        operation.setElevationAngle("111");
        operation.setAzimuth("222");
        operation.setShootCount(3);
        operation.setShootType("扇形");
        operations.add(operation);

        Operation operation1 = new Operation();
        operation1.setId(2);
        operation1.setDeviceName("bbb");
        operation1.setElevationAngle("1");
        operation1.setAzimuth("2");
        operation1.setShootCount(3);
        operation1.setShootType("扇形");
        operations.add(operation1);

        map.put("rows", operations);
        map.put("page", 1);
        map.put("total", 2);
        map.put("records", 10);

        if (isSuccess) {
            message = "success";
        }
        map.put("message", message);
        return map;
    }

    @RequiresRoles("admin")
    @PostMapping("/sendMessage")
    @ResponseBody
    public Object sendMessage(Model model, Operation operation, String messageType) {
        String message = "faild";
        Map<String, Object> map = new HashMap<>();
        boolean isSuccess = true;

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("device_name",operation.getDeviceName());
        requestMap.put("elevation_angle",operation.getElevationAngle());
        requestMap.put("azimuth",operation.getAzimuth());
        requestMap.put("shoot_count",operation.getShootCount());
        requestMap.put("shoot_type",operation.getShootType());
        requestMap.put("message_type",messageType);
        requestMap.put("create_date",new Date());

        InsertArgs insertArgs = new InsertArgs(" operation_record_t",requestMap);
        operationService.addRecord(insertArgs);

        if (isSuccess) {
            message = "success";
        }
        map.put("message", message);
        return map;
    }

    @GetMapping("/records")
    @ResponseBody
    public Object searchRecords(Model model, Operation operation, @RequestParam int rows, @RequestParam int page){

        Map<String,String> params = new HashMap<String,String>();
        params.put("device_name",operation.getDeviceName());
        params.put("message_type",operation.getMessageType());

        PagedSearchResult searchResult = operationService.queryListByPage("operation_record_t",page,rows,params,Operation.class);
        return searchResult;
    }
}
