package com.heartland.test.exceptions;

import com.heartland.test.enums.ERRS;

import java.util.ArrayList;
import java.util.List;

public class DeviceException extends RuntimeException {

    private final List<ERRS> errors = new ArrayList<>();

    public DeviceException() {
    }

    public List<ERRS> getErrors() {
        return errors;
    }
}
