/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.tyj.jhpt.server.body.dto;

import java.util.Date;

/**
 * 驱动电机数据
 *
 * @author: CK
 * @date: 2017/12/8
 */
public class QuDongDianJiDto {
    private String carVin;
    private Date eventTime;
    // 驱动电机序号
    private byte seq;
    // 驱动电机状态
    private byte status;
    // 驱动电机控制器温度
    private byte controlTemperature;
    // 驱动电机转速
    private int speed;
    // 驱动电机转矩
    private int zhuanju;
    // 驱动电机温度
    private byte temperature;
    // 电机控制器输入电压
    private int dianya;
    // 电机控制器直流母线母线电流
    private int dianliu;

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public byte getSeq() {
        return seq;
    }

    public void setSeq(byte seq) {
        this.seq = seq;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getControlTemperature() {
        return controlTemperature;
    }

    public void setControlTemperature(byte controlTemperature) {
        this.controlTemperature = controlTemperature;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getZhuanju() {
        return zhuanju;
    }

    public void setZhuanju(int zhuanju) {
        this.zhuanju = zhuanju;
    }

    public byte getTemperature() {
        return temperature;
    }

    public void setTemperature(byte temperature) {
        this.temperature = temperature;
    }

    public int getDianya() {
        return dianya;
    }

    public void setDianya(int dianya) {
        this.dianya = dianya;
    }

    public int getDianliu() {
        return dianliu;
    }

    public void setDianliu(int dianliu) {
        this.dianliu = dianliu;
    }
}
