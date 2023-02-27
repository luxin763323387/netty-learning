package com.cn.lx.bugstack.netty.server.handler;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author StevenLu
 * @date 2023/2/26 11:33 下午
 */
public class ChannelHandler {

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
