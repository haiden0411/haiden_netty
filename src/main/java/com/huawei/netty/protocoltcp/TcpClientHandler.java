package com.huawei.netty.protocoltcp;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * Author：胡灯
 * Date：2020-08-01 9:15
 * Description：<描述>
 */
@Slf4j
public class TcpClientHandler extends SimpleChannelInboundHandler<MessageProtocol>
{
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        String msg = "今天天气冷，吃火锅";
        byte[] content = msg.getBytes(CharsetUtil.UTF_8);
        int len = content.length;
        for (int i = 0; i < 5; i++)
        {
            //创建协议包
            MessageProtocol protocol = new MessageProtocol();
            protocol.setLen(len);
            protocol.setContent(content);
            ctx.writeAndFlush(protocol);
        }
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception
    {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        log.info("客户端接收消息：");
        log.info("长度："+len);
        log.info("内容："+new String(content,CharsetUtil.UTF_8));
        log.info("客户端接收的数量："+(++count));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        log.info("异常信息："+cause.getMessage());
    }


}
