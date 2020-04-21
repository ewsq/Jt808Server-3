package com.dcw.jt808server.jt808;

import android.text.TextUtils;

import com.dcw.jt808server.Tools;


/**
 * @author lixiaobin
 * @date 2019-10-18 14:05
 * @desc 描述信息
 */
public class Parser {
    /**
     * 7EH 《————》 7DH+02H；
     * 7DH 《————》 7DH+01H；
     *
     * @param str 16 进制
     * @return
     */
    public static byte[] tropeYXByte(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (!TextUtils.isEmpty(str)) {
            str = str.toUpperCase();
            str = str.replace("7D", "7D01");
            str = str.replace("7E", "7D02");

        }

        byte[] result = new byte[str.length() / 2];

        str = Tools.replaceStr(str, "7D", "7D01");
        str = Tools.replaceStr(str, "7E", "7D02");
        result = Tools.parseHexStr2Byte(str);
        return result;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        String sre = sb.toString();
        sb.setLength(0);
        return sre;
    }

    /**
     * 校验码指从消息头开始，同后一字节异或，直到校验码前一个字节，占用一个字节
     * @param src
     * @param start
     *      开始
     * @param end
     *     结束
     * @return
     */
    public static byte generateCheckCode(byte[] src,int start, int end) {
        byte ret = src[start];
        for (int j = start+1; j < end; j++) {
            ret = (byte) (ret ^ src[j]);
        }
        return ret;
    }

    /** */
    /**
     * @函数功能: 10进制串转为BCD码
     * @输入参数: 10进制串
     * @输出结果: BCD码
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }

        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;

        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }

            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    /**
     * 高地位反相排序
     *
     * @param bytes
     * @return
     */
    public static byte[] sortToByte(byte[] bytes) {
        byte[] des = new byte[bytes.length];
        int i = 0;
        for (int j = bytes.length - 1; j >= 0; j--) {
            des[i] = bytes[j];
            i++;
        }
        return des;
    }

    /**
     * 将整数转换成二进制字节（先低字节后高字节）
     *
     * @param iSource
     * @return
     */
    public static byte[] int2Bytes(int iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];
        for (int i = 0; (i < 4) && (i < iArrayLen); i++) {
            bLocalArr[i] = (byte) (iSource >> 8 * i & 0xFF);
        }
        return bLocalArr;
    }

    /**
     *
     * @param a
     * @param n
     * 位数
     * @return
     */
    public static String getHexInt(int a,int n) {
        if (n > 10) {
            n = 10;
        }
        String ret = Integer.toHexString(a);
        if (!TextUtils.isEmpty(ret)) {
            while (ret.length() < n) {
                ret = "0" + ret;
            }
        }
        return  ret;
    }

    public static String getHexMsgId(int msgId) {
        return "0x" + getHexInt(msgId, 4).toUpperCase();
    }

}
