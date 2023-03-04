package com.cn.lx.bugstack.udp.server;

import com.cn.lx.bugstack.udp.coder.UdpPacketDecoder;
import com.cn.lx.bugstack.udp.server.handler.MyServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author StevenLu
 * @date 2023/3/3 12:37 上午
 */
public class UdpServer {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup(10);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(boss).
                    channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)    //广播
                    .option(ChannelOption.SO_RCVBUF, 2048 * 1024)// 设置UDP读缓冲区为2M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)// 设置UDP写缓冲区为1M
                    .handler(new ChannelInitializer<NioDatagramChannel>() {

                        private EventLoopGroup group1 = new NioEventLoopGroup();
                        @Override
                        protected void initChannel(NioDatagramChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 解码转String，注意调整自己的编码格式GBK、UTF-8
                            //pipeline.addLast("stringDecoder", new StringDecoder(Charset.forName("GBK")));

                            pipeline.addLast(new UdpPacketDecoder());
                            pipeline.addLast(group1,new MyServerHandler());
                        }
                    });
            ChannelFuture sync = bootstrap.bind(7393).sync();
            sync.channel().closeFuture().sync();
        } finally {
            //优雅的关闭释放内存
            boss.shutdownGracefully();
        }
    }
}
