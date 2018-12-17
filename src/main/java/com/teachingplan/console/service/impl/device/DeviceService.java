package com.teachingplan.console.service.impl.device;

import com.teachingplan.console.beans.PagedSearchResult;
import com.teachingplan.console.beans.device.Device;
import com.teachingplan.console.dao.device.DeviceDao;
import com.teachingplan.console.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeviceService  extends BaseService {

    @Autowired
    private DeviceDao deviceDao;

    public boolean modDevice(Device device) {
        deviceDao.modDevice(device);
        return true;
    }

    public void delDevice(int id) {
        deviceDao.delDevice(id);
    }
}
