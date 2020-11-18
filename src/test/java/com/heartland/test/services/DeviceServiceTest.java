package com.heartland.test.services;

import com.heartland.test.dtos.DeviceDTO;
import com.heartland.test.enums.ERRS;
import com.heartland.test.exceptions.DeviceException;
import com.heartland.test.model.Device;
import com.heartland.test.repositories.DeviceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.BeanUtils;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class DeviceServiceTest {

    private DeviceRepository deviceRepository;

    private DeviceDTO validDeviceDTO;
    private DeviceDTO notFoundDeviceDTO;

    @BeforeEach
    void setUp() {
        //set up the repository
        deviceRepository = Mockito.mock(DeviceRepository.class);



        //valid find device use case
        Device.DevicePkId validDevicePkId = new Device.DevicePkId("0-1435665", "Lab4-001");
        Device validDevice = new Device(validDevicePkId, "Lab 4 Scanner");
        validDeviceDTO = new DeviceDTO();
        BeanUtils.copyProperties(validDevice, validDeviceDTO);
        BeanUtils.copyProperties(validDevicePkId, validDeviceDTO);
        when(deviceRepository.findById(validDevicePkId)).thenReturn(Optional.of(validDevice));

        //not found device
        Device.DevicePkId notFoundDevicePkId = new Device.DevicePkId("123-123", "Office-243-Printer");
        notFoundDeviceDTO = new DeviceDTO();
        BeanUtils.copyProperties(notFoundDevicePkId, notFoundDeviceDTO);
        when(deviceRepository.findById(notFoundDevicePkId)).thenReturn(Optional.empty());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addDevice() {
    }

    @Test
    void updateDevice() {
    }

    @Test
    void findDevice() {
        //valid device
        DeviceService service = new DeviceService(deviceRepository);
        DeviceDTO deviceDTO = service.findDevice(validDeviceDTO.getMachineCode(), validDeviceDTO.getSerialNumber());
        assertTrue(new ReflectionEquals(validDeviceDTO).matches(deviceDTO));

        //undefined device
        DeviceException e = assertThrows(DeviceException.class, () -> {
            service.findDevice(notFoundDeviceDTO.getMachineCode(), notFoundDeviceDTO.getSerialNumber());
        });
        assertTrue(e.getErrors() != null
                && e.getErrors().size() == 2
                && e.getErrors().contains(ERRS.ERR002)
                && e.getErrors().contains(ERRS.ERR004));
    }
}
