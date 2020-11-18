package com.heartland.test.controllers;

import com.heartland.test.dtos.DeviceDTO;
import com.heartland.test.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/device")
@Validated
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/")
    public Object addDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        return deviceService.addDevice(deviceDTO);
    }

    @PutMapping("/")
    public Object updateDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        return deviceService.updateDevice(deviceDTO);
    }

    @GetMapping("/{serialnumber}/{machinecode}")
    public Object findDevice(@PathVariable("machinecode") @NotEmpty(message = "ERR001") String machineCode,
                             @PathVariable("serialnumber") @Pattern(regexp = "^[0-9]+[-][0-9]+$", message = "ERR003") String serialNumber) {
        return deviceService.findDevice(machineCode, serialNumber);
    }
}
