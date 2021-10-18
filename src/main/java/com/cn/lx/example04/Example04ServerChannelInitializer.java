package com.cn.lx.example04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class Example04ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //增加 "\t" 分割
        //DelimiterBasedFrameDecoder是将特殊的字符作为消息的分隔符，本例中用到的是”\t”
        ByteBuf byteBuf = Unpooled.copiedBuffer("\t".getBytes());
        //DelimiterBasedFrameDecoder 参数
        //maxFrameLength：解码的帧的最大长度
        //stripDelimiter：解码时是否去掉分隔符
        //failFast：为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
        //delimiter：分隔符
        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(2048,byteBuf));

        // 字符串解码 和 编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        // 自己的逻辑Handler
        pipeline.addLast("handler", new Example04ServerHandler());


    }
}
