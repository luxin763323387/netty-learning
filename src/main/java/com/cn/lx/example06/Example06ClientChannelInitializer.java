package com.cn.lx.example06;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class Example06ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //IdleStateHandler  可以对三种类型的心跳检测
        //1）readerIdleTime：为读超时时间（即测试端一定时间内未接受到被测试端消息）
        //2）writerIdleTime：为写超时时间（即测试端一定时间内向被测试端发送消息）
        //3）allIdleTime：所有类型的超时时间
        pipeline.addLast("handler", new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast(new Example06ClientHandler());

    }
}
