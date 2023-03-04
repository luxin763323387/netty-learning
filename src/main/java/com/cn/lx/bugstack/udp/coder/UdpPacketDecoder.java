package com.cn.lx.bugstack.udp.coder;

import com.cn.lx.bugstack.udp.protocol.Packet;
import com.cn.lx.bugstack.udp.protocol.PacketCodeC;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author StevenLu
 * @date 2023/3/3 1:37 下午
 */
public class UdpPacketDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
        Packet res = PacketCodeC.INSTANCE.decode(packet);
        out.add(res);
    }
}
