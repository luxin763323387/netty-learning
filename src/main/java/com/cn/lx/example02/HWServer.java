package com.cn.lx.example02;

import com.cn.lx.example01.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HWServer {

    private int port;

    public HWServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        HWServer server = new HWServer(7788);
        server.start();
    }

    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup wortGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap()
                .group(bossGroup, wortGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer());

        try {
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            wortGroup.shutdownGracefully();
        }
    }
}
