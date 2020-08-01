package com.huawei.netty.zerocopy;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;
/**
 * Author：胡灯
 * Date：2020-07-22 23:25
 * Description：<描述>
 */
public class OldClient
{
    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket("127.0.0.1", 6666);
        String filename = "d:/test/aa/aa.zip";
        FileInputStream inputStream = new FileInputStream(filename);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long readCount = 0;
        long total = 0;
        long startTime = System.currentTimeMillis();
        while ((readCount = inputStream.read(buffer)) > 0)
        {
            total += readCount;
            dataOutputStream.write(buffer);
        }

        System.out.println("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
