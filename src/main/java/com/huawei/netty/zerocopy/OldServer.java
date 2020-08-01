package com.huawei.netty.zerocopy;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
/**
 * Author：胡灯
 * Date：2020-07-22 23:25
 * Description：<描述>
 */
public class OldServer
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(6666);
        while (true)
        {
            Socket socket = serverSocket.accept();
            System.out.println("开始接收数据: "+ LocalDateTime.now());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[4096];
            while (true)
            {
                int count = dataInputStream.read(bytes);
                if (count==-1)
                {
                    break;
                }
            }
            System.out.println("结束接收数据: "+ LocalDateTime.now());
        }
    }
}
