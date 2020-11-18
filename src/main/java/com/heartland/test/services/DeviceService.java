package com.heartland.test.services;

import com.heartland.test.dtos.DeviceDTO;
import com.heartland.test.enums.ERRS;
import com.heartland.test.exceptions.DeviceException;
import com.heartland.test.model.Device;
import com.heartland.test.repositories.DeviceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceDTO addDevice(DeviceDTO deviceDTO) {
        Device device = new Device();
        BeanUtils.copyProperties(device, deviceDTO);
        Device.DevicePkId devicePkId = new Device.DevicePkId();
        BeanUtils.copyProperties(devicePkId, deviceDTO);

        device = deviceRepository.save(device);

        deviceDTO = new DeviceDTO();
        BeanUtils.copyProperties(deviceDTO, device);
        BeanUtils.copyProperties(deviceDTO, device.getDevicePkId());
        return deviceDTO;
    }

    public DeviceDTO updateDevice(DeviceDTO deviceDTO) {
        Device.DevicePkId devicePkId = new Device.DevicePkId();
        BeanUtils.copyProperties(devicePkId, deviceDTO);
        String serialNumber = deviceDTO.getSerialNumber();
        String machineCode = deviceDTO.getMachineCode();
        Device device = deviceRepository.findById(devicePkId).orElseThrow(() -> checkForMissingSerialNumberAndMachineCode(serialNumber, machineCode));

        BeanUtils.copyProperties(device, deviceDTO);
        device = deviceRepository.save(device);

        deviceDTO = new DeviceDTO();
        BeanUtils.copyProperties(deviceDTO, device);
        BeanUtils.copyProperties(deviceDTO, device.getDevicePkId());
        return deviceDTO;
    }

    public DeviceDTO findDevice(String machineCode, String serialNumber) {
        Device.DevicePkId devicePkId = new Device.DevicePkId(serialNumber, machineCode);

        Device device = deviceRepository.findById(devicePkId).orElseThrow(() -> checkForMissingSerialNumberAndMachineCode(serialNumber, machineCode));
        DeviceDTO deviceDTO = new DeviceDTO();
        BeanUtils.copyProperties(device, deviceDTO);
        BeanUtils.copyProperties(device.getDevicePkId(), deviceDTO);
        return deviceDTO;
    }

    //helper method for determining if serialnumber and/or machine code exist at all in the database if no records found
    private DeviceException checkForMissingSerialNumberAndMachineCode(String serialNumber, String machineCode) {
        //this is to conform to requirement 4, which says if either or both machine code/serial number
        //are not found in database, then return appropriate errors in response
        //this feels like it contradicts requirement 1 and normally I would ask for clarification beforehand
        DeviceException exception = new DeviceException();
        List<Device> devices = deviceRepository.findAllByDevicePkId_MachineCode(machineCode);
        if (CollectionUtils.isEmpty(devices)) {
            exception.getErrors().add(ERRS.ERR002);
        }
        devices = deviceRepository.findAllByDevicePkId_SerialNumber(serialNumber);
        if (CollectionUtils.isEmpty(devices)) {
            exception.getErrors().add(ERRS.ERR004);
        }

        return exception;
    }
}
