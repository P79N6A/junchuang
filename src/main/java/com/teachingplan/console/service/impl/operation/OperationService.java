package com.teachingplan.console.service.impl.operation;

import com.teachingplan.console.beans.device.Device;
import com.teachingplan.console.dao.device.DeviceDao;
import com.teachingplan.console.dao.operation.OperationDao;
import com.teachingplan.console.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationService extends BaseService {

    @Autowired
    private OperationDao operationeDao;


}
