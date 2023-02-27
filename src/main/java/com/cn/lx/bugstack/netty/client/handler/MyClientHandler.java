package com.cn.lx.bugstack.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author StevenLu
 * @date 2023/2/27 10:04 下午
 */
public class MyClientHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        //通知客户端链消息发送成功
        String str = "客户端收到：" + new Date() + " " + msg + "\r\n";
        ctx.writeAndFlush(str);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开链接：" + ctx.channel().localAddress().toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：本客户端链接到服务端信息。channelId:" + channel.id());
        System.out.println("链接报告信息：链接报告ip: " + channel.localAddress().getHostString());
        System.out.println("链接报告信息：链接报告port: " + channel.localAddress().getPort());
        System.out.println("链接报告完毕");

        // 通知服务端链接成功
        String str = "通知服务端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(str);
    }
}
