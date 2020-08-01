package com.huawei.netty.zerocopy;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
/**
 * Author：胡灯
 * Date：2020-07-22 23:48
 * Description：<描述>
 */
public class NewServer
{
    public static void main(String[] args) throws Exception
    {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (true)
        {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount = 0;
            while (readCount != -1)
            {
                readCount = socketChannel.read(buffer);
                buffer.rewind();
            }
        }



    }
}
