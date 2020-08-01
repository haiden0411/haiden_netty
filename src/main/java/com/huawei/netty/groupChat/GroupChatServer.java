package com.huawei.netty.groupChat;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
/**
 * Author：胡灯
 * Date：2020-07-21 23:37
 * Description：<描述>
 */
public class GroupChatServer
{
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;
    public GroupChatServer()
    {
        try
        {
           selector = Selector.open();
           listenChannel = ServerSocketChannel.open();
           listenChannel.socket().bind(new InetSocketAddress(PORT));
           listenChannel.configureBlocking(false);
           listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listen(){
        try
        {
            while (true)
            {
                int count = selector.select();
                if (count>0) //有事件处理
                {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext())
                    {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable())
                        {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector,SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress()+" 上线");
                        }
                        if (key.isReadable())
                        {
                            //处理读
                            readData(key);
                        }
                        //防止重复处理
                        iterator.remove();
                    }
                }else {
                    System.out.println("等待。。。");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {

        }
    }
    //读取客户端消息
    private void readData(SelectionKey key){
        SocketChannel channel = null;
        channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try
        {
            int count = channel.read(buffer);
            if (count>0)
            {
                String msg = new String(buffer.array());
                System.out.println("from客户端："+msg);
                //向其它的客户转发消息
                sendToOtherClients(msg,channel);

            }
        }
        catch (IOException e)
        {
            try
            {
                System.out.println(channel.getRemoteAddress() + " 离线了...");
                key.cancel();
                channel.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    //转发消息
    private void sendToOtherClients(String msg,SocketChannel self) throws IOException
    {
        System.out.println("服务器转发消息");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        for (SelectionKey key : selector.keys())
        {
            Channel target =  key.channel();
            if (target instanceof SocketChannel &&  target!=self)
            {

                SocketChannel dest = (SocketChannel) target;
                dest.write(ByteBuffer.wrap(msg.getBytes()));
            }
        }
    }
    public static void main(String[] args)
    {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
