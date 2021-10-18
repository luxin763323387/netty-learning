package com.cn.lx.example01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class HelloWorldClient {

    private int port;
    private String address;

    public HelloWorldClient(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public static void main(String[] args) {
        HelloWorldClient client = new HelloWorldClient(7788, "172.16.0.189");
        client.start();
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .handler(new ClientChannelInitializer());

        try {
            Channel channel = bootstrap.bind(address, port).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String msg = reader.readLine();
                if (msg == null) {
                    continue;
                }
                channel.writeAndFlush(msg + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
