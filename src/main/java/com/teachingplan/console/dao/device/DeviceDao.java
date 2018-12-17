package com.teachingplan.console.dao.device;

import com.teachingplan.console.beans.device.Device;

public interface DeviceDao {
    void modDevice(Device device);

    void delDevice(int id);
}
