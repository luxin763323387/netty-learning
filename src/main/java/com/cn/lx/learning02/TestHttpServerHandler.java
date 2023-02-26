package com.cn.lx.learning02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author StevenLu
 * @date 2021/11/2 下午10:19
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        //判断msg 是不是 httpRequest 请求
        if (msg instanceof HttpRequest) {
            System.out.println(" msg 类型=" + msg.getClass());
            System.out.println(" 客户端地址" + ctx.channel().remoteAddress());

            HttpRequest httpRequest = (HttpRequest) msg;
            //获取uri
            URI uri = new URI(httpRequest.getUri());
            if("favicon.ico".equals(uri.getPath())){
                System.out.println("请求了 favicon.ico 不做相应");
                return;
            }

            //回复休息给浏览器[http协议]
            ByteBuf copiedBuffer = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

            //构造一个http相应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, copiedBuffer);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,copiedBuffer.readableBytes());

            //将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }
}
