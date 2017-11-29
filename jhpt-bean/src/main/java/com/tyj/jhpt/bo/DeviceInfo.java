/*
 * Copyright (c) 2017. juzhen.io. All rights reserved.
 */

package com.tyj.jhpt.bo;

/**
 * This class corresponds to the database table `device_info`
 */
public class DeviceInfo extends DeviceInfoBase {
    private byte type;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}