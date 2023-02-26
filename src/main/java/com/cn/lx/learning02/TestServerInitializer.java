package com.cn.lx.learning02;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author StevenLu
 * @date 2021/11/2 下午10:06
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        //向管道加入处理器

        //得到容器
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个netty的 httpServerCode => [coder - decoder]
        //HttpServerCodec
        //1、 HttpServerCodec 是 netty提供的处理http的编码解码器
        pipeline.addLast("myHttpServerCode",new HttpServerCodec());

        //2、增加一个自定义handler
        pipeline.addLast("myTestHttpServerHandler",new TestHttpServerHandler());
    }
}
