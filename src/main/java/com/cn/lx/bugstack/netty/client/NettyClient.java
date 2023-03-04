package com.cn.lx.bugstack.netty.client;

import com.cn.lx.bugstack.netty.client.handler.MyInMsgHandler;
import com.cn.lx.bugstack.netty.client.handler.MyOutMsgHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;


/**
 * @author StevenLu
 * @date 2023/2/27 9:24 下午
 */
public class NettyClient {

    public static void main(String[] args) {
        new NettyClient().connect("127.0.0.1", 9000);
    }

    private void connect(String inetHost, int inetPort) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    // 基于换行符号
                    channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    // 解码转String，注意调整自己的编码格式GBK、UTF-8
                    channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
                    // 解码转String，注意调整自己的编码格式GBK、UTF-8
                    channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                    // 在管道中添加我们自己的接收数据实现方法
                    channel.pipeline().addLast(new MyOutMsgHandler());//消息出站处理器，在Client发送消息时候会触发此处理器
                    channel.pipeline().addLast(new MyInMsgHandler()); //消息入站处理器
                }
            });
            ChannelFuture f = b.connect(inetHost, inetPort).sync();
            System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}

