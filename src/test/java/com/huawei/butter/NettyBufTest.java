package com.huawei.butter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
/**
 * Author：胡灯
 * Date：2020-07-26 10:40
 * Description：<描述>
 */
@Slf4j
public class NettyBufTest
{
    @Test
    public void testBuf01(){
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++)
        {
            buffer.writeByte(i);
        }
       log.info("capcity:"+buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++)
        {
            System.out.println(buffer.readByte());
        }
    }
    @Test
    public void testBuf02(){
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world,北京", CharsetUtil.UTF_8);
        if (byteBuf.hasArray())
        {
            byte[] array = byteBuf.array();
            String s = new String(array, CharsetUtil.UTF_8);
            System.out.println("bytebuf:"+byteBuf);
        }
    }
}
