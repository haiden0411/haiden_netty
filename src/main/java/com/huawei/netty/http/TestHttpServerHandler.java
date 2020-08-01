package com.huawei.netty.http;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.nio.channels.SocketChannel;
/**
 * Author：胡灯
 * Date：2020-07-25 22:53
 * Description：<描述>
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception
    {
        System.out.println("channel:"+ctx.channel()+" pipline:"+ctx.pipeline()+" from channel pipline:"+ctx.channel().pipeline());
        System.out.println("current handler:"+ctx.handler());
        if (msg instanceof HttpRequest)
        {
            System.out.println("ctx 类型："+ ctx.getClass());
            System.out.println("pipline hash:"+ctx.pipeline().hashCode()+ " Testhttpserverhandler hash :"+this.hashCode());
            System.out.println("msg类型:" + msg.getClass());
            System.out.println("客户端地址："+ctx.channel().remoteAddress());
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath()))
            {
                System.out.println("请求了favicon，不响应了");
                return;
            }
            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            ctx.writeAndFlush(response);
        }
    }
}
