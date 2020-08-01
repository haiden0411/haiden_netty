package com.huawei.netty.nio;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
/**
 * Author：胡灯
 * Date：2020-07-21 21:35
 * Description：<描述>
 */
public class NIOclient
{
    public static void main(String[] args) throws Exception
    {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        if (!socketChannel.connect(inetSocketAddress))
        {
            if (!socketChannel.finishConnect())
            {
                System.out.println("因为连接需要，客户端不会阻塞，可以做其他工作");
            }
        }
        //连接成功，就发现数据
        String str = "hello,haiden~~~";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(buffer);
        System.in.read();
    }
}
