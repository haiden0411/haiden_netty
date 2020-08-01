package com.huawei.netty.protocoltcp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
/**
 * Author：胡灯
 * Date：2020-08-01 10:16
 * Description：<描述>
 */
@Slf4j
public class MyMessageDecoder extends ReplayingDecoder<Void>
{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
       log.info("MyMessageDecoder 方法被调用");
        //二进制的字节码转成
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(content);
        out.add(messageProtocol);
    }
}
