package com.cn.lx.learning02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.SynchronousQueue;

/**
 * @author StevenLu
 * @date 2021/11/2 下午9:56
 */
public class NettyServer {

    private int port;

    public NettyServer(int port){
        this.port = port;

    }

    private void start(){

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new TestServerInitializer());

        try {
            ChannelFuture sync = bootstrap.bind(port).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(7788);
        nettyServer.start();
    }
}
