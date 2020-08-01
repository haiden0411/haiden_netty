package com.huawei.netty.tcp;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
/**
 * Author：胡灯
 * Date：2020-08-01 9:15
 * Description：<描述>
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<ByteBuf>
{
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception
    {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer,CharsetUtil.UTF_8);
        System.out.println("客户端接收消息：" + message);
        System.out.println("客户端接收消息数量:"+(++count));
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        //发送10条
        for (int i = 0; i < 10; i++)
        {
            ByteBuf buffer = Unpooled.copiedBuffer("hello,server" + i, CharsetUtil.UTF_8);
            ctx.writeAndFlush(buffer);
        }
    }

}
