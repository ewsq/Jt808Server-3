package com.dcw.jt808server.jt808;

/**
 * @author lixiaobin
 * @date 2019-10-17 16:02
 * @desc 描述信息
 */
public abstract class DCWMessage {
    public byte[] mSrcBytes;
    public int serialid;

    public abstract DCWPacket pack();
    public abstract void unpack(DataPacket payload);
}
