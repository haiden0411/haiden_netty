package com.huawei.netty.http;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
/**
 * Author：胡灯
 * Date：2020-07-25 22:52
 * Description：<描述>
 */
public class TestHttpServer
{
    public static void main(String[] args) throws InterruptedException
    {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try
        {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,worker).channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            channelFuture.channel().closeFuture().sync();

        }
        finally
        {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
