package com.cn.lx.bugstack.udp.serializer;

/**
 * @author StevenLu
 * @date 2023/3/3 3:10 下午
 */
public interface Serializer {

    <T> T deserialize(Class<T> clazz, String msg);
}
