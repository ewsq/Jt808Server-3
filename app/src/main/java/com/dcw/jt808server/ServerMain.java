package com.dcw.jt808server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * @author lixiaobin
 * @date 2020-04-21 17:08:55
 * @desc 描述信息
 */
public class ServerMain {

    public static final int PORT_BUS = 52483;
    public static final int PORT_RTP_VIDEO = 52467;
    public static final int PORT_RTP_AUDIO = 52468;
    public static final int MAX_BUS_FRAME_LENGTH = 2048;

    public static void main(String[] args) {

        try {
            NettyServer busServer = initBusServer();
//            NettyServer rtpAudioServer= initRtpAudioServer();
//            NettyServer rtpVideoServer = initRtpVideoServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static NettyServer initBusServer() throws Exception {
        ByteBuf delimiter1 = Unpooled.copiedBuffer(new byte[]{0x7E});
        ByteBuf delimiter2 = Unpooled.copiedBuffer(new byte[]{0x7E, 0x7E});
        DelimiterBasedFrameDecoder busDelimiterHandler = new DelimiterBasedFrameDecoder(MAX_BUS_FRAME_LENGTH, delimiter1, delimiter2);
        BusHandler busHandler = new BusHandler();
        NettyServer busServer = new NettyServer(busDelimiterHandler,busHandler);
        busServer.bind(PORT_BUS);
        return busServer;
    }

    private static NettyServer initRtpVideoServer() throws Exception {
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{0x30, 0x31, 0x63, 0x64});
        DelimiterBasedFrameDecoder delimiterHandler = new DelimiterBasedFrameDecoder(MAX_BUS_FRAME_LENGTH, delimiter);
        BusHandler busHandler = new BusHandler();
        NettyServer busServer = new NettyServer(delimiterHandler,busHandler);
        busServer.bind(PORT_BUS);
        return busServer;
    }

    private static NettyServer initRtpAudioServer() throws Exception {
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{0x30, 0x31, 0x63, 0x64});
        DelimiterBasedFrameDecoder delimiterHandler = new DelimiterBasedFrameDecoder(MAX_BUS_FRAME_LENGTH, delimiter);
        BusHandler busHandler = new BusHandler();
        NettyServer busServer = new NettyServer(delimiterHandler,busHandler);
        busServer.bind(PORT_BUS);
        return busServer;
    }
}
