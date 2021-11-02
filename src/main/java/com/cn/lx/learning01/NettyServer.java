package com.cn.lx.learning01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author StevenLu
 * @date 2021/10/25 下午9:41
 */
public class NettyServer {

    private int port;

    public NettyServer(int port) {
        this.port = port;
    }


    private void start() {
        //创建bossGroup 和 workGroup
        //说明
        //1、创建2个线程组 bossGroup 和 workerGroup
        //2、bossGroup 只处理链接请求，真正和客户端业务处理，会交给workerGroup完成
        //3、两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        //创建服务器的启动类，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup) //设置2个线程组
                .channel(NioServerSocketChannel.class)  //NioSocketChannel 作为服务器通道实现
                .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列等待链接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true) //保持活动链接状态
                .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new NettyServerHandler());
                    }
                });//给我我们的workerGroup 的EventLoop 对应管道的处理器

        System.out.println(".....服务器 is ready.....");

        try {
            //生成一个 channelFuture 对象
            ChannelFuture cf = bootstrap.bind(port).sync();
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(6668);
        nettyServer.start();
    }
}
