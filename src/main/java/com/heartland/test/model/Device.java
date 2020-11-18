package com.heartland.test.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(schema = "dbo", name = "Device")
public class Device implements Serializable {

    private final static long serialVersionUID = 1;

    public Device() {
        //default constructor
    }

    public Device(DevicePkId devicePkId, String deviceName) {
        this.devicePkId = devicePkId;
        this.deviceName = deviceName;
    }

    //this satisfies requirement 1, which is to consider the serial number and machine code columns to be the primary key
    //although requirements 1 and 4 feel like they contradict one another, normally I would ask for clarification here
    @EmbeddedId
    private DevicePkId devicePkId;

    @Column(name = "DeviceName", nullable = false)
    private String deviceName;

    public DevicePkId getDevicePkId() {
        return devicePkId;
    }

    public void setDevicePkId(DevicePkId devicePkId) {
        this.devicePkId = devicePkId;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Embeddable
    public static class DevicePkId implements Serializable {

        private final static long serialVersionUID = 1;

        public DevicePkId() {
            //empty constructor
        }

        public DevicePkId(String serialNumber,
                          String machineCode) {
            this.serialNumber = serialNumber;
            this.machineCode = machineCode;
        }

        @Column(name = "SerialNumber", nullable = false)
        private String serialNumber;

        @Column(name = "MachineCode", nullable = false)
        private String machineCode;
    }
}
