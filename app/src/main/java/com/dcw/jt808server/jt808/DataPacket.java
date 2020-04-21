package com.dcw.jt808server.jt808;

import android.text.TextUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author lixiaobin
 * @date 2019-10-17 15:31
 * @desc 数据包封装
 */
public class DataPacket {
    private static final byte UNSIGNED_BYTE_MIN_VALUE = 0;
    private static final short UNSIGNED_BYTE_MAX_VALUE = Byte.MAX_VALUE - Byte.MIN_VALUE;

    private static final short UNSIGNED_SHORT_MIN_VALUE = 0;
    private static final int UNSIGNED_SHORT_MAX_VALUE = Short.MAX_VALUE - Short.MIN_VALUE;

    private static final int UNSIGNED_INT_MIN_VALUE = 0;
    private static final long UNSIGNED_INT_MAX_VALUE = (long) Integer.MAX_VALUE - Integer.MIN_VALUE;

    private static final long UNSIGNED_LONG_MIN_VALUE = 0;

    public static final int MAX_PAYLOAD_SIZE = 512;

    public ByteBuffer payload;
    public int index;

    public DataPacket() {
        payload = ByteBuffer.allocate(MAX_PAYLOAD_SIZE);
    }

    public ByteBuffer getData() {
        return payload;
    }

    public int size() {
        return payload.position();
    }

    public synchronized void add(byte c) {
        if (!payload.hasRemaining()) {
            //剩余空间不够增加长度
            ByteBuffer newBuffer = ByteBuffer.allocate(payload.position() + MAX_PAYLOAD_SIZE);
            payload.flip(); //要将posision 归0 不然没有有效数据可以put
            newBuffer.put(payload);
            payload = newBuffer;
        }
        payload.put(c);
    }

    public synchronized void putBytes(byte[] bytes) {
        if (null != bytes) {
            for (byte b : bytes) {
                add(b);
            }
        }
    }

    public void resetIndex() {
        index = 0;
    }

    public byte getByte() {
        byte result = 0;
        result |= (payload.get(index + 0) & 0xFF);
        index += 1;
        return result;
    }


    public short getShort() {
        short result = 0;
        result |= (payload.get(index + 0) & 0xFF) << 8;
        result |= (payload.get(index + 1) & 0xFF);
        index += 2;
        return result;
    }

    public int getUnsignedShort(){
        int result = 0;
        result |= (payload.get(index + 0) & 0xFF) << 8;
        result |= (payload.get(index + 1) & 0xFF);
        index += 2;
        return result;
    }

    public int getInt() {
        int result = 0;
        result |= (payload.get(index + 0) & 0xFF) << 24;
        result |= (payload.get(index + 1) & 0xFF) << 16;
        result |= (payload.get(index + 2) & 0xFF) << 8;
        result |= (payload.get(index + 3) & 0xFF);
        index += 4;
        return result;
    }

    public long getUnsignedInt(){
        long result = 0;
        result |= (payload.get(index + 0) & 0xFFFFL) << 24;
        result |= (payload.get(index + 1) & 0xFFFFL) << 16;
        result |= (payload.get(index + 2) & 0xFFFFL) << 8;
        result |= (payload.get(index + 3) & 0xFFFFL);
        index += 4;
        return result;
    }

    public long getLong() {
        long result = 0;
        result |= (payload.get(index + 0) & 0xFFFFL) << 56;
        result |= (payload.get(index + 1) & 0xFFFFL) << 48;
        result |= (payload.get(index + 2) & 0xFFFFL) << 40;
        result |= (payload.get(index + 3) & 0xFFFFL) << 32;
        result |= (payload.get(index + 4) & 0xFFFFL) << 24;
        result |= (payload.get(index + 5) & 0xFFFFL) << 16;
        result |= (payload.get(index + 6) & 0xFFFFL) << 8;
        result |= (payload.get(index + 7) & 0xFFFFL);
        index += 8;
        return result;
    }

    public long getUnsignedLong(){
        return getLong();
    }

    public long getLongReverse() {
        long result = 0;
        result |= (payload.get(index + 7) & 0xFFFFL) << 56;
        result |= (payload.get(index + 6) & 0xFFFFL) << 48;
        result |= (payload.get(index + 5) & 0xFFFFL) << 40;
        result |= (payload.get(index + 4) & 0xFFFFL) << 32;
        result |= (payload.get(index + 3) & 0xFFFFL) << 24;
        result |= (payload.get(index + 2) & 0xFFFFL) << 16;
        result |= (payload.get(index + 1) & 0xFFFFL) << 8;
        result |= (payload.get(index + 0) & 0xFFFFL);
        index += 8;
        return result;
    }

    public float getFloat() {
        return Float.intBitsToFloat(getInt());
    }

    public void putByte(byte data) {
        add(data);
    }

    public void putUnsignedByte(short data){
        if(data < UNSIGNED_BYTE_MIN_VALUE || data > UNSIGNED_BYTE_MAX_VALUE){
            throw new IllegalArgumentException("Value is outside of the range of an unsigned byte: " + data);
        }

        putByte((byte) data);
    }

    public void putShort(short data) {
        add((byte) (data >> 8));
        add((byte) (data >> 0));
    }

    public void putUnsignedShort(int data){
        if(data < UNSIGNED_SHORT_MIN_VALUE || data > UNSIGNED_SHORT_MAX_VALUE){
            throw new IllegalArgumentException("Value is outside of the range of an unsigned short: " + data);
        }

        putShort((short) data);
    }

    public void putInt(int data) {
        add((byte) (data >> 24));
        add((byte) (data >> 16));
        add((byte) (data >> 8));
        add((byte) (data >> 0));
    }

    public void putUnsignedInt(long data){
        if(data < UNSIGNED_INT_MIN_VALUE || data > UNSIGNED_INT_MAX_VALUE){
            throw new IllegalArgumentException("Value is outside of the range of an unsigned int: " + data);
        }

        putInt((int) data);
    }

    public void putLong(long data) {
        add((byte) (data >> 56));
        add((byte) (data >> 48));
        add((byte) (data >> 40));
        add((byte) (data >> 32));
        add((byte) (data >> 24));
        add((byte) (data >> 16));
        add((byte) (data >> 8));
        add((byte) (data >> 0));
    }

    public void putUnsignedLong(long data){
        if(data < UNSIGNED_LONG_MIN_VALUE){
            throw new IllegalArgumentException("Value is outside of the range of an unsigned long: " + data);
        }

        putLong(data);
    }

    public void putFloat(float data) {
        putInt(Float.floatToIntBits(data));
    }

    public void putString(String cmdParam, String charset) {
        if (TextUtils.isEmpty(charset)) {
            charset = "gbk";
        }
        if (!TextUtils.isEmpty(cmdParam)) {
            byte[] gbks = cmdParam.getBytes(Charset.forName("gbk"));
            for (int i = 0; i < gbks.length; i++) {
                add(gbks[i]);
            }
        }
    }
}
