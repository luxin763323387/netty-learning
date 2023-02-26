package com.cn.lx.bugstack.netty.server;

import com.cn.lx.bugstack.netty.server.handler.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @author StevenLu
 * @date 2023/2/26 8:41 下午
 */
public class NettyServer {

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.bind(9000);
    }

    private void bind(int port) {

        // 老板就是 bossGroup 负责接口， workerGroup 负责处理
        // bossGroup 表示监听端口，accept 新连接的线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // workerGroup 表示处理每一条连接的数据读写的线程组
        NioEventLoopGroup work = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, work)
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 指定IO模型
                    .channel(NioServerSocketChannel.class)
                    // 我们调用childHandler()方法，给这个引导类创建一个ChannelInitializer，这里主要就是定义后续每条连接的数据读写
                    // childHandler() 为 workGroup 中的 Channel 绑定处理器，同上
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /* 解码器 */
                            // 基于换行符号
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            // 字节码转String 继承了MessageToMessageDecoder
                            ch.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
                            // 字节码转String 继承了MessageToMessageDecoder
                            ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                            //在管道中添加我们自己的接收数据实现方法
                            ch.pipeline().addLast(new MyServerHandler());
                        }
                    });
            ChannelFuture f = serverBootstrap.bind(port).sync();
            System.out.println("netty-server started");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }
}
