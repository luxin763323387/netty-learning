package com.cn.lx.bugstack.udp.server.handler;

import com.alibaba.fastjson.JSON;
import com.cn.lx.bugstack.udp.protocol.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author StevenLu
 * @date 2023/3/3 12:59 上午
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Packet> {
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//         System.out.println("" + Thread.currentThread() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " UDP服务端接收到消息：" + msg);
//    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {

        System.out.println("" + Thread.currentThread() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " UDP服务端接收到消息：" + JSON.toJSONString(packet));

        //String msg = packet.content().toString(Charset.forName("GBK"));
        //System.out.println("" + Thread.currentThread() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " UDP服务端接收到消息：" + msg);

//        //向客户端发送消息
//        String json = "服务端已收到信息" + msg;
//        // 由于数据报的数据是以字符数组传的形式存储的，所以传转数据
//        byte[] bytes = json.getBytes(Charset.forName("GBK"));
//        DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), packet.sender());
//        ctx.writeAndFlush(data);//向客户端发送消息
    }
}
