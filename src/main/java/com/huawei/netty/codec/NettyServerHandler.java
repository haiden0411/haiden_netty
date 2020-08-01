package com.huawei.netty.codec;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
/**
 * Author：胡灯
 * Date：2020-07-23 23:27
 * Description：<描述>
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student>
{
    @Override
    public void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception
    {
        //从客户端发送的student
        System.out.println("客户端发送的数据 id="+msg.getId()+" 名字="+msg.getName());

    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端,喵1 ",CharsetUtil.UTF_8));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        ctx.close();
    }
}
