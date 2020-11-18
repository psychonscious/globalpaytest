package com.heartland.test.enums;

public enum ERRS {

    ERR001("machine.code.invalid",
            "ER001",
            "The machine code is incorrect. Check the Machine code you provided and try again."),
    ERR002("machine.code.not.found",
            "ER002",
            "The machine code does not match our records."),
    ERR003("serial.number.invalid",
            "ER003",
            "The serial number entered can include a - z, A - Z, 0 - 9 and hyphen. Please correct your entry."),
    ERR004("serial.number.not.found",
            "ER004",
            "The serial number does not match our records.");


    ERRS(String resourceKey,
                String errorCode,
                String message) {
        this.resourceKey = resourceKey;
        this.errorCode = errorCode;
        this.message = message;
    }

    private final String resourceKey;
    private final String errorCode;
    private final String message;

    public String getResourceKey() {
        return resourceKey;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
