package com.cn.lx.bugstack.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author StevenLu
 * @date 2023/2/26 9:00 下午
 */
public class MyServerHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //当有客户链接后，添加到channelGroup通信组
        //ChannelHandler.channelGroup.add(ctx.channel());
        // netty的SocketChannel
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");
        // 通知简历客户端链接 建立成功
        // 因为实现StringEncode
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
//        ByteBuf buf = Unpooled.buffer(str.getBytes().length);
//        buf.writeBytes(str.getBytes("GBK"));
//        ctx.writeAndFlush(buf);
        //ctx.writeAndFlush(str);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        // readableBytes获取可用字节数量，初始化字节数组
//        byte[] bytes = new byte[buf.readableBytes()];
//        // 将buf写入到bytes 容器中
//        buf.readBytes(bytes);
//        System.out.println(new Date() + "接收到消息：" );
//        System.out.println(new String(bytes, Charset.forName("GBK")) );
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);

        // 通知客户端发送消息成功
        // 因为实现了encode编码
        String str = "服务端收到：" + new Date() + " " + msg + "\r\n";
//        ByteBuf buf = Unpooled.buffer(str.getBytes().length);
//        buf.writeBytes(str.getBytes("GBK"));

        //ctx.writeAndFlush(str);
        // 从DefaultChannelGroup 获取Channel 群发
        //ChannelHandler.channelGroup.writeAndFlush(str);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开链接" + ctx.channel().localAddress().toString());
        //ChannelHandler.channelGroup.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }
}
