package com.huawei.netty.tcp;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;
/**
 * Author：胡灯
 * Date：2020-08-01 9:19
 * Description：<描述>
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<ByteBuf>
{
    private static int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception
    {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("服务器接收到数据："+message);
        System.out.println("服务接收到消息量："+ (++this.count));
        ByteBuf response = Unpooled.copiedBuffer(UUID.randomUUID().toString()+" ", CharsetUtil.UTF_8);
        ctx.writeAndFlush(response);
    }
}
