package com.huawei.netty.groupChat;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
/**
 * Author：胡灯
 * Date：2020-07-22 21:24
 * Description：<描述>
 */
public class GroupChartClient
{
    private final String HOST="127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;
    public GroupChartClient() throws IOException
    {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username+" is ok");

    }

    public void sendInfo(String info){
        info = username + " 说: "+info;
        try
        {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void readInfo(){
        try
        {
            int readChannels = selector.select();
            if (readChannels>0)
            {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                if (iterator.hasNext())
                {
                    SelectionKey key = iterator.next();
                    if (key.isReadable())
                    {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
                iterator.remove();
            }else {

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException
    {
        GroupChartClient chatClient = new GroupChartClient();

        new Thread(() -> {
            while (true)
            {
                chatClient.readInfo();

                try
                {
                    Thread.currentThread().sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

        //发关数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine())
        {
            String info = scanner.nextLine();
            chatClient.sendInfo(info);
        }
    }
}
