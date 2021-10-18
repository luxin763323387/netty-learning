package com.cn.lx.example02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HWClient {

    private int port;
    private String address;


    public HWClient(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public static void main(String[] args) {
        HWClient client = new HWClient(7788, "127.0.0.1");
        client.start();
    }

    public void start() {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer());


        try {
            // 开启客户端监听，连接到远程节点，阻塞等待直到连接完成
            ChannelFuture future = bootstrap.connect(address, port).sync();
            future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
            //阻塞等待数据，直到channel关闭(客户端关闭)
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }


    }
}
