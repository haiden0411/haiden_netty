package com.huawei.netty.protocoltcp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
/**
 * Author：胡灯
 * Date：2020-08-01 10:11
 * Description：<描述>
 */
@Slf4j
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol>
{
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception
    {
        log.info("MyMessageEncoder方法被调用");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
