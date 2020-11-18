package com.heartland.test.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class DeviceDTO {

    public DeviceDTO() {
        //empty constructor
    }

    //group these together as they form the primary key in the entity
    //this meets requirement 2, although the error message spec underneath mentions that letters are also acceptable
    //normally, I would ask for clarification in this case
    @Pattern(regexp = "^[0-9]+[-][0-9]+$", message = "ERR003")
    private String serialNumber;
    //this meets requirement 3
    @NotEmpty(message = "ERR001")
    private String machineCode;

    private String deviceName;


    public String getSerialNumber() {
        return serialNumber;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
