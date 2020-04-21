package com.dcw.jt808server;

import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiaobin
 * @date 2020-04-21 18:16:24
 * @desc 描述信息
 */
public class NettyChannelMap {
    private static Map<String, SocketChannel> map=new ConcurrentHashMap<String, SocketChannel>();
    public  void add(String clientId,SocketChannel socketChannel){
        map.put(clientId,socketChannel);
    }
    public Channel get(String clientId){
        return map.get(clientId);
    }
    public void remove(SocketChannel socketChannel){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==socketChannel){
                map.remove(entry.getKey());
            }
        }
    }

}