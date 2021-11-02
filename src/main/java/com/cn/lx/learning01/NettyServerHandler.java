package com.cn.lx.learning01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author StevenLu
 * @date 2021/10/25 下午10:29
 * 1、我们自己定义的一个Handler 需要基础netty 规定好的某个HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //writeAndFlush() = write() + flush()
        //把数据写入缓存，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端～",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常 一般需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 读取客户端数据(当客户端发送数据时)
     * Object msg：时客户端发送的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("server ctx = " + ctx);
        //将msg 转成一个byteBuf（netty）
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息: "+ buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址: "+ ctx.channel().remoteAddress());
    }
}
