package com.huawei.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Author：胡灯
 * Date：2020-07-19 22:10
 * Description：<描述>
 */
public class BioServer
{
    public static void main(String[] args) throws IOException
    {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动:端口:"+serverSocket.getLocalPort());
        while (true)
        {
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            threadPool.execute(() -> handler(socket));
        }
    }

    public static void handler(Socket socket){

        try
        {
            System.out.println("线程id:" + Thread.currentThread().getId() + ",名称:" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true)
            {
                System.out.println("线程id:" + Thread.currentThread().getId() + ",名称:" + Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if (read!=-1)
                {
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }finally
        {
            System.out.println("关闭client连接");
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
