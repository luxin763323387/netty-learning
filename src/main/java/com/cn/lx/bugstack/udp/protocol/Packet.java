package com.cn.lx.bugstack.udp.protocol;

import java.net.InetSocketAddress;

/**
 * @author StevenLu
 * @date 2023/3/3 1:39 下午
 */
public abstract class Packet {

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    private InetSocketAddress inetSocketAddress;

    /**
     * 获取指令
     *
     * @return
     */
    public abstract Byte getCommand();
}
