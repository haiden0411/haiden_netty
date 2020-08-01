package com.huawei.netty.heartbeat;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
/**
 * Author：胡灯
 * Date：2020-07-26 22:38
 * Description：<描述>
 */
public class MyserverHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        if (evt instanceof IdleStateEvent)
        {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state())
            {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"--超时事件发生 "+eventType);
            System.out.println("服务器作相应的处理");
        }
    }
}
