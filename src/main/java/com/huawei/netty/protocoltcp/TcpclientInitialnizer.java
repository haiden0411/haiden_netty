package com.huawei.netty.protocoltcp;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
/**
 * Author：胡灯
 * Date：2020-08-01 9:11
 * Description：<描述>
 */
public class TcpclientInitialnizer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyMessageEncoder());
        pipeline.addLast(new MyMessageDecoder());
        pipeline.addLast(new TcpClientHandler());
    }
}
