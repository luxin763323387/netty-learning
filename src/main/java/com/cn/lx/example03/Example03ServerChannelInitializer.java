package com.cn.lx.example03;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Example03ServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //解决拆包、粘包问题
        //ineBasedFrameDecoder的工作原理是它依次遍历ByteBuf 中的可读字节，判断看是否有”\n” 或者” \r\n”，如果有，
        // 就以此位置为结束位置，从可读索引到结束位置区间的字节就组成了一行。
        pipeline.addLast(new LineBasedFrameDecoder(2048));

        // 字符串解码 和 编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        // 自己的逻辑Handler
        pipeline.addLast("handler", new Example03ServerHandler());
    }
}
