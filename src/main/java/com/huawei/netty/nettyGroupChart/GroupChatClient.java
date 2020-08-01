package com.huawei.netty.nettyGroupChart;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;
/**
 * Author：胡灯
 * Date：2020-07-26 11:31
 * Description：<描述>
 */
public class GroupChatClient
{
    private final String host;
    private final int port;
    public GroupChatClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException
    {
       EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try
        {
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception
                        {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("stringdecoder",new StringDecoder());
                            pipeline.addLast("stringencoder",new StringEncoder());
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            Channel channel = future.channel();
            System.out.println("-----"+channel.localAddress()+"------");
            //创建一个扫描对象
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine())
            {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg + "\r\n");
            }
        }
        finally
        {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception
    {
        new GroupChatClient("192.168.1.107",7000).run();
    }
}
