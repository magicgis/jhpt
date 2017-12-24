/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.tyj.jhpt.vo;

import com.github.fartherp.framework.core.vo.BaseVo;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/12/24
 */
public class RealTimePageVo extends BaseVo {
    private String plateNo;
    private String startDate;
    private String endDate;

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
