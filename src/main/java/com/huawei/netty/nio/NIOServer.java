package com.huawei.netty.nio;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
/**
 * Author：胡灯
 * Date：2020-07-21 21:34
 * Description：<描述>
 */
public class NIOServer
{
    public static void main(String[] args) throws Exception
    {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        //绑定商品
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待
        while (true)
        {
            if (selector.select(1000)==0)
            {
                System.out.println("服务器等待了1s,不等了");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext())
            {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable())
                {
                    //生成socketchannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功,hashcode:"+socketChannel.hashCode());
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable())
                {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from client:" + new String(buffer.array()));
                }
                //防止重复操作
                keyIterator.remove();
            }

        }
    }
}
