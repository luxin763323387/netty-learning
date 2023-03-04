package com.cn.lx.bugstack.udp.protocol;

import com.cn.lx.bugstack.udp.command.Command;
import com.cn.lx.bugstack.udp.serializer.Serializer;
import com.cn.lx.bugstack.udp.serializer.impl.VehicleStatusSerializerImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;

import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据包类型
 *
 * @author StevenLu
 * @date 2023/3/3 3:05 下午
 */
public class PacketCodeC {

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    public static final ConcurrentHashMap<Byte, Class<? extends Packet>> packetTypeMap;

    public static final ConcurrentHashMap<Byte, Serializer> serializerTypeMap;

    static {
        packetTypeMap = new ConcurrentHashMap<>();
        packetTypeMap.put(Command.STATUS, VehicleStatusPacket.class);

        serializerTypeMap = new ConcurrentHashMap<>();
        serializerTypeMap.put(Command.STATUS, new VehicleStatusSerializerImpl());
    }


    public Packet decode(DatagramPacket packet) {
        ByteBuf byteBuf = packet.content();

        // 获取指令
        byte command = byteBuf.readByte();
        // 跳过两位
        byteBuf.skipBytes(1);
        String msg = packet.content().toString(Charset.forName("GBK"));
        Class<? extends Packet> requestType = packetTypeMap.get(command);
        Serializer serializer = serializerTypeMap.get(command);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, msg);
        }
        return null;
    }
}
