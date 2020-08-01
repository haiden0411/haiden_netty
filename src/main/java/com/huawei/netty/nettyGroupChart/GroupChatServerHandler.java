package com.huawei.netty.nettyGroupChart;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Author：胡灯
 * Date：2020-07-26 11:44
 * Description：<描述>
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String>
{
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
    {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch ->{
            if (channel!=ch)
            {
                ch.writeAndFlush("[客户端]"+channel.remoteAddress()+" "+formatter.format(LocalDateTime.now())+ " 发送消息:"+msg+"\n");
            }else {
                ch.writeAndFlush("[自己]发送的消息"+msg + "\n");
            }
        });
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        Channel channel = ctx.channel();
        //将客户端加入聊天的消息推送给在线的其他客户
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+" "+formatter.format(LocalDateTime.now())+"加入聊天\n");
        channelGroup.add(channel);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        Channel channel = ctx.channel();
        //将客户端加入聊天的消息推送给在线的其他客户
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+" "+channel.remoteAddress()+formatter.format(LocalDateTime.now())+"离开了\n");
        System.out.println("channel group size:"+channelGroup.size());
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println(ctx.channel().remoteAddress()+" "+formatter.format(LocalDateTime.now())+" 上线了~");
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println(ctx.channel().remoteAddress()+" "+formatter.format(LocalDateTime.now())+" 下线了~");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        ctx.close();
    }
}
