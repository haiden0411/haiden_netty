package com.huawei.netty.zerocopy;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
/**
 * Author：胡灯
 * Date：2020-07-22 23:48
 * Description：<描述>
 */
public class NewClient
{
    public static void main(String[] args) throws Exception
    {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",6666));
        String filename = "d:/test/aa/aa.zip";
        FileChannel channel = new FileInputStream(filename).getChannel();
        long startTime = System.currentTimeMillis();
        System.out.println("filesize："+channel.size());
        long maxsize = 1024*1024*8;
        long fileSize = channel.size();
        long i = fileSize%maxsize==0?(fileSize/maxsize):(fileSize/maxsize+1);
        long transferCount = 0;
        for (long l = 0; l < i; l++)
        {
            long readCount =  channel.transferTo(l*maxsize, maxsize, socketChannel);
            transferCount +=readCount;
        }
        System.out.println("发送的总的字节数 =" + transferCount + " 耗时:" + (System.currentTimeMillis() - startTime));

        //关闭
        channel.close();
    }

}
