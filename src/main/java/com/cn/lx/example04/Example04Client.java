package com.cn.lx.example04;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Example04Client {

    private  int port;
    private  String address;

    public Example04Client(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new Example04ClientChannelInitializer());

        try {
            ChannelFuture future = bootstrap.connect(address, port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Example04Client example04Client = new Example04Client(7788,"127.0.0.1");
        example04Client.start();
    }


}
