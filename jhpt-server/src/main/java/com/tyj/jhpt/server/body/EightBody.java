/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.tyj.jhpt.server.body;

import com.tyj.jhpt.bo.Dianya;
import com.tyj.jhpt.server.body.dto.DianYaDto;
import com.tyj.jhpt.server.body.dto.DianYasDto;
import com.tyj.jhpt.server.message.MessageBean;
import com.tyj.jhpt.server.message.type.RealTimeMessage;
import com.tyj.jhpt.service.DianyaService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.tyj.jhpt.server.body.EightBody.DataEnum.dianliu;
import static com.tyj.jhpt.server.body.EightBody.DataEnum.dianya;
import static com.tyj.jhpt.server.body.EightBody.DataEnum.ownSimpleTotal;
import static com.tyj.jhpt.server.body.EightBody.DataEnum.seq;
import static com.tyj.jhpt.server.body.EightBody.DataEnum.simpleTotal;
import static com.tyj.jhpt.server.body.EightBody.DataEnum.systemNo;

/**
 * 可充电储能装置电压数据
 *
 * @author: CK
 * @date: 2017/12/8
 */
@Component
public class EightBody extends AbstractBody<DianYasDto> {
    public EightBody() {
        super(RealTimeMessage.DIANYA.getCode());
    }

    @Resource(name = "dianyaService")
    DianyaService dianyaService;

    public DianYasDto deal(MessageBean mb) {
        DianYasDto dtos = new DianYasDto();
        byte[] content = mb.getContent();
        int offset = 0;
        // 可充电储能子系统个数
        dtos.setNumber(content[offset + 1]);
        offset += 1;

        for (int i = 0; i < dtos.getNumber(); i++) {
            DianYaDto dto = new DianYaDto();
            // 可充电储能子系统号
            dto.setSystemNo(content[offset + systemNo.length]);
            offset += systemNo.length;

            // 可充电储能装置电压
            byte[] bytes = new byte[dianya.length];
            System.arraycopy(content, offset, bytes, 0, dianya.length);
            offset += dianya.length;
            BigInteger bigInteger = new BigInteger(bytes);
            int dianya = bigInteger.intValue();
            dto.setDianya(dianya);

            // 可充电储能装置电流
            bytes = new byte[dianliu.length];
            System.arraycopy(content, offset, bytes, 0, dianliu.length);
            offset += dianliu.length;
            bigInteger = new BigInteger(bytes);
            int dianliu = bigInteger.intValue();
            dto.setDianliu(dianliu);

            // 单体电池总数
            bytes = new byte[simpleTotal.length];
            System.arraycopy(content, offset, bytes, 0, simpleTotal.length);
            offset += simpleTotal.length;
            bigInteger = new BigInteger(bytes);
            int simpleTotal = bigInteger.intValue();
            dto.setDianliu(simpleTotal);

            // 单体电池总数
            bytes = new byte[seq.length];
            System.arraycopy(content, offset, bytes, 0, seq.length);
            offset += seq.length;
            bigInteger = new BigInteger(bytes);
            int seq = bigInteger.intValue();
            dto.setSeq(seq);

            // 本帧单体电池总数
            dto.setOwnSimpleTotal(content[offset + ownSimpleTotal.length]);
            offset += ownSimpleTotal.length;

            bytes = new byte[dto.getOwnSimpleTotal()];
            System.arraycopy(content, offset, bytes, 0, dto.getOwnSimpleTotal());
            offset += dto.getOwnSimpleTotal();
            dto.setSimpleTotals(bytes);

            dtos.addDto(dto);
        }

        if (CollectionUtils.isNotEmpty(dtos.getList())) {
            List<Dianya> list = new ArrayList<Dianya>();
            for (DianYaDto dto : dtos.getList()) {
                Dianya bo = new Dianya();
                BeanUtils.copyProperties(dto, bo);
                list.add(bo);
            }
            dianyaService.saveBatch(list);
        }
        return dtos;
    }

    public static enum DataEnum {
        systemNo(1, "可充电储能子系统号"),
        dianya(4, "可充电储能装置电压"),
        dianliu(4, "可充电储能装置电流"),
        simpleTotal(4, "单体电池总数"),
        seq(4, "本帧起始电池序号"),
        ownSimpleTotal(1, "本帧单体电池总数"),
        ;
        private int length;
        private String desc;
        DataEnum(int length, String desc) {
            this.length = length;
            this.desc = desc;
        }
    }
}
