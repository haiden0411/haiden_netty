package com.huawei.netty.nettyGroupChart;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 * Author：胡灯
 * Date：2020-07-26 14:04
 * Description：<描述>
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
    {
        System.out.println(msg.trim());
    }
}
