package com.dcw.jt808server.jt808.msg;

import android.text.TextUtils;


import com.dcw.jt808server.Tools;
import com.dcw.jt808server.jt808.DCWMessage;
import com.dcw.jt808server.jt808.DCWPacket;
import com.dcw.jt808server.jt808.DataPacket;
import com.dcw.jt808server.jt808.entity.MediaResource;

import java.util.List;

/**
 * @author lixiaobin
 * @date 2019-11-28 15:22
 * @desc 描述信息
 */
public class MsgUploadResourceList1205 extends DCWMessage {
    public static final int ID_UPLOAD_RESOURCELIST = 0x1205;

    /**
     * 流水号 对应查询音视频资源列表的流水号
     */
    public int serialId;

    /**
     * 总数
     */
    public int resourceCount;

    public List<MediaResource> resources;

    public boolean isMultiParts;
    public int seqPart;



    @Override
    public DCWPacket pack() {
        DCWPacket packet = new DCWPacket();
        packet.msgId = ID_UPLOAD_RESOURCELIST;
        packet.isMultiPacket = isMultiParts;
        packet.seqPacket = seqPart;
        packet.payload.putUnsignedShort(serialId);
        if (null == resources) {
            resourceCount = 0;
        }

        packet.payload.putInt(resourceCount);


        if (null != resources) {
            for (MediaResource resource : resources) {
                packet.payload.putByte((byte) resource.channel);

                byte[] startAt = new byte[6];
                if (!TextUtils.isEmpty(resource.startAt)) {
                    byte[] bytes = Tools.str2Bcd(resource.startAt);
                    System.arraycopy(bytes,0,startAt,0,Math.min(startAt.length,bytes.length));
                }
                for (int i = 0; i < startAt.length; i++) {
                    packet.payload.putByte(startAt[i]);
                }

                byte[] endAt = new byte[6];
                if (!TextUtils.isEmpty(resource.endAt)) {
                    byte[] bytes = Tools.str2Bcd(resource.endAt);
                    System.arraycopy(bytes,0,endAt,0,Math.min(endAt.length,bytes.length));
                }
                for (int i = 0; i < endAt.length; i++) {
                    packet.payload.putByte(endAt[i]);
                }

                packet.payload.putUnsignedLong(resource.alarmSign);
                packet.payload.putByte((byte) resource.sourceType);
                packet.payload.putByte((byte) resource.streamType);
                packet.payload.putByte((byte) resource.storageType);
                packet.payload.putInt(resource.fileSize);
            }
        }
        packet.len = packet.payload.size();
        return packet;
    }

    @Override
    public void unpack(DataPacket payload) {

    }

    @Override
    public String toString() {
        return "MsgUploadResourceList1205 上传文件信息【上行】{" +
                "serialId=" + serialId +
                ", resourceCount=" + resourceCount +
                ", resources=" + resources +
                ", isMultiParts=" + isMultiParts +
                ", seqPart=" + seqPart +
                '}';
    }
}
