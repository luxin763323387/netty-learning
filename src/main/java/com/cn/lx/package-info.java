/**
 * Created by StevenLu
 * <p>
 * example01 第一个package是一个netty的helloWord例子 {@link com.cn.lx.example01.HelloWordServer},{@link com.cn.lx.example01.HelloWorldClient}
 * <p>
 * example02 关于ChannelPipeLine和ChannelHandler例子 {@link com.cn.lx.example02.HWServer},{@link com.cn.lx.example02.HWClient}
 * <p>
 * example03 关于TCP拆包和粘包现象展示，使用LineBasedFrameDecoder解决 {@link com.cn.lx.example03.Example03Server},{@link com.cn.lx.example03.Example03Client}
 * <p>
 * example04 使用DelimiterBasedFrameDecoder解决粘包拆包的问题  {@link com.cn.lx.example04.Example04Server},{@link com.cn.lx.example04.Example04Client}
 * 1、消息长度固定，累计读取到消息长度总和为定长Len的报文之后即认为是读取到了一个完整的消息。计数器归位，重新读取。
 * 2、将回车换行符作为消息结束符。
 * 3、将特殊的分隔符作为消息分隔符，回车换行符是他的一种。
 * 4、通过在消息头定义长度字段来标识消息总长度。
 * <p>
 * example05 netty Http请求  {@link com.cn.lx.example05.Example05Server}
 * <p>
 * example06 netty 心跳检测 {@link com.cn.lx.example06.Example06Server}，{@link com.cn.lx.example06.Example06Client}
 */
package com.cn.lx;