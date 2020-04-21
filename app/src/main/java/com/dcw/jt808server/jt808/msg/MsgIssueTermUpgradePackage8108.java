package com.dcw.jt808server.jt808.msg;



import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author lixiaobin
 * @date 2019-10-28 14:18
 * @desc 下发终端升级包 【下行】
 *
 * 对该命令终端使用通用应答确认是否正确收
 * 到升级包数据。
 */
public class MsgIssueTermUpgradePackage8108 extends DCWMessage {

    public static final int ID_ISSUE_TERM_UPGRAD_PACKAGE = 0x8108;

    public int upgradeType;
    public byte[] productorId = new byte[5];
    public int versionLen;
    public String version;
    public int packageLen;
    public byte[] packageData;

    public MsgIssueTermUpgradePackage8108(DCWPacket packet) {
        packet.msgId = ID_ISSUE_TERM_UPGRAD_PACKAGE;
        unpack(packet.payload);
    }

    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_ISSUE_TERM_UPGRAD_PACKAGE;
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {
        payload.resetIndex();
        upgradeType = payload.getByte();
        for (int i = 0; i < productorId.length; i++) {
            productorId[i] = payload.getByte();
        }
        versionLen = payload.getByte();
        if (versionLen > 0) {
            byte[] versionBytes = new byte[versionLen];
            for (int i = 0; i < versionLen; i++) {
                versionBytes[i] = payload.getByte();
            }
            version = new String(versionBytes, Charset.forName("utf-8"));
        }

        packageLen = payload.getInt();
        packageData = new byte[packageLen];
        for (int i = 0; i < packageLen; i++) {
            packageData[i] = payload.getByte();
        }
    }

    @Override
    public String toString() {
        return "MsgIssueTermUpgradePackage8108 下发终端升级包{" +
                "upgradeType=" + upgradeType +
                ", productorId=" + Arrays.toString(productorId) +
                ", versionLen=" + versionLen +
                ", version='" + version + '\'' +
                ", packageLen=" + packageLen +
                ", packageData=" + Arrays.toString(packageData) +
                '}';
    }
}
