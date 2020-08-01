package com.huawei.netty.http;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpServerCodec;
/**
 * Author：胡灯
 * Date：2020-07-25 22:53
 * Description：<描述>
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("myhttpserverCode",new HttpServerCodec());
        pipeline.addLast("myTesthandler",new TestHttpServerHandler());
        System.out.println("ok~~~~~");
    }
}
