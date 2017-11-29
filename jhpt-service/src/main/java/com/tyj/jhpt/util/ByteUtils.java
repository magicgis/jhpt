package com.tyj.jhpt.util;

import com.github.fartherp.framework.security.ISecurity;
import com.github.fartherp.framework.security.symmetry.ThreeDES;
import io.netty.buffer.ByteBuf;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/5/3
 */
public class ByteUtils {
    /**
     * 字节转换成int，采用小端模式转换
     * @param content
     * @param offset
     * @return
     */
    public static int byteToIntLE(byte[] content, int offset) {
        int id = (0xff000000 & content[offset] << 24 )
                + (0xff0000	& content[offset + 1] << 16)
                + (0xff00	& content[offset + 2] << 8)
                + (0xff 	& content[offset+3]);
        return id;
    }

    /**
     * asc码到byte
     * @param b
     * @return
     */
    public static byte asciiToByteValue(byte b) {
        int a = 0xff & b;
        if (a >= '0' && a <= '9') {
            b = (byte) (a - 48);
        } else if (a >= 'A' && a <= 'Z') {
            b = (byte) (a - 55);
        } else if (a >= 'a' && a <= 'z') {
            b = (byte) (a - 87);
        }
        return b;
    }

    /**
     * 一位二进制数据被编码成两个字节的ascii码，本方法将ascii码转换成原始的二进制数据
     *
     * @param bytes
     */
    public static byte[] asciiCharsToBytes(byte[] bytes) {
        byte[] bs = new byte[bytes.length / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = ByteUtils.asciiToByteValue(bytes[i]);
        }
        for (int i = 0; i < bytes.length; i += 2) {
            bs[i / 2] = (byte) ((0xff & bytes[i]) << 4 | 0xff & bytes[i + 1]);
        }
        return bs;
    }

    public static int bytesToInt(byte[] deviceId) {
        int a = 0;
        for (int i = 0; i < 4; i++) {
            a += (deviceId[i] & 0xff) << ((3 - i) * 8);
        }
        return a;
    }

    public static void putIntToBufferBigEndian(byte[] bb, int offset, int x) {
        bb[offset] = (byte)(x >> 24);
        bb[(offset + 1)] = (byte)(x >> 16);
        bb[(offset + 2)] = (byte)(x >> 8);
        bb[(offset + 3)] = (byte)x;
    }

    public static void putByteToBuffer(byte[] bb, byte x, int index) {
        bb[index] = x;
    }

    public static byte[] readBytes(ByteBuf in, byte actualLength) {
        byte[] bs = new byte[actualLength * 2];
        in.readBytes(bs);
        return ByteUtils.asciiCharsToBytes(bs);
    }

    public static int readInt(ByteBuf in) {
        byte[] deviceId = new byte[8];
        in.readBytes(deviceId);
        deviceId = ByteUtils.asciiCharsToBytes(deviceId);
        return ByteUtils.bytesToInt(deviceId);
    }

    public static byte readByte(ByteBuf in) {
        byte[] bs = new byte[2];
        in.readBytes(bs);
        return ByteUtils.asciiCharsToBytes(bs)[0];
    }

    /**
     * 将协议数据里的内容解密后再传进来
     *
     * @param encryptIndex
     * @param content
     * @return
     */
    public static byte[] decode(byte encryptIndex, byte[] content) {
        if (encryptIndex == 0) {
            return content;
        }
        ISecurity iSecurity = new ThreeDES(new byte[]{encryptIndex});
        return iSecurity.decrypt(content);
    }

    /**
     * 使用给定的密钥序号对内容进行加密
     *
     * @param encryptIndex
     * @param content
     * @return
     */
    public static byte[] encrypt(byte encryptIndex, byte[] content) {
        content = completeData(content);
        ISecurity iSecurity = new ThreeDES(new byte[]{encryptIndex});
        return iSecurity.encrypt(content);
    }

    /**
     * 按照加密算法的要求，加密的数据块必须是8个字节的整数倍。这里对加密的数据块进行补全
     *
     * @param content
     * @return
     */
    public static byte[] completeData(byte[] content) {
        if (content.length % 8 == 0) {
            return content;
        }
        byte[] newContent = new byte[content.length + (8 - content.length % 8)];
        System.arraycopy(content, 0, newContent, 0, content.length);
        newContent[content.length] = (byte) 0x80;
        for (int i = content.length + 1; i < newContent.length; i++) {
            newContent[i] = 0x00;
        }
        return newContent;
    }
}