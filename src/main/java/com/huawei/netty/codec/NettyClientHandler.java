package com.huawei.netty.codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
/**
 * Author：胡灯
 * Date：2020-07-23 23:53
 * Description：<描述>
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("豹子头林冲").build();
        ctx.writeAndFlush(student);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复消息："+ buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
