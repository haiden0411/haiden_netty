package com.huawei.netty.nettyGroupChart;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
/**
 * Author：胡灯
 * Date：2020-07-26 11:31
 * Description：<描述>
 */
public class GroupChatServer
{
    private int port;

    public GroupChatServer(int port)
    {
        this.port = port;
    }

    public void run() throws InterruptedException
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try
        {
            bootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception
                        {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());
                            //加入自己的业务处理
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });
            System.out.println("群聊服务启动.....");
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        }
        finally
        {
           bossGroup.shutdownGracefully();
           workGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException
    {
        new GroupChatServer(7000).run();
    }
}
