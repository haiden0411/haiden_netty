package com.huawei.netty.protocoltcp;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
/**
 * Author：胡灯
 * Date：2020-08-01 9:19
 * Description：<描述>
 */
@Slf4j
public class TcpServerHandler extends SimpleChannelInboundHandler<MessageProtocol>
{
    private static int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception
    {
        int len = msg.getLen();
        byte[] content = msg.getContent();

        log.info("服务器接收到数据：");
        log.info("长度:" + len);
        log.info("内容:"+ new String(content,CharsetUtil.UTF_8));
        log.info("服务器接收到消息包数量："+(++count));
        String response = UUID.randomUUID().toString();
        int responseLen = response.getBytes(CharsetUtil.UTF_8).length;
        MessageProtocol responseProtocol = new MessageProtocol();
        responseProtocol.setLen(responseLen);
        responseProtocol.setContent(response.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(responseProtocol);
    }
}
