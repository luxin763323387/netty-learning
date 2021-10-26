package com.cn.lx.example06;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Example06Server {

    private int port;

    public Example06Server(int port){
        this.port = port;
    }

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap()
                .group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new Example06ServerChannelInitializer());

        try {
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Example06Server example06Server = new Example06Server(7788);
        example06Server.start();
    }

}
