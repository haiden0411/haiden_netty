package com.huawei.netty.simple;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
/**
 * Author：胡灯
 * Date：2020-07-23 23:27
 * Description：<描述>
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        /*System.out.println("读取线程:"+Thread.currentThread().getName());
        System.out.println("sever ctx = "+ctx);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
        */
        ctx.channel().eventLoop().execute(() -> {
            try
            {
                Thread.sleep(10*1000);
            }
            catch (InterruptedException e)
            {
                System.out.println("发生异常");
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 喵2:"+ LocalDateTime.now() +"\n",CharsetUtil.UTF_8));
        });

        ctx.channel().eventLoop().schedule(() ->{
            try
            {
                Thread.sleep(5*1000);
            }
            catch (InterruptedException e)
            {
                System.out.println("发生异常");
            }
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 喵3:"+ LocalDateTime.now() +"\n",CharsetUtil.UTF_8));
        } ,5, TimeUnit.SECONDS);

        System.out.println("go on");
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
