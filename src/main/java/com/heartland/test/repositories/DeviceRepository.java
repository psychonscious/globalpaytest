package com.heartland.test.repositories;

import com.heartland.test.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Device.DevicePkId> {

    List<Device> findAllByDevicePkId_MachineCode(String machineCode);

    List<Device> findAllByDevicePkId_SerialNumber(String serialNumber);
}
