package com.huawei.netty.bio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Author：胡灯
 * Date：2020-07-19 23:09
 * Description：<描述>
 */
public class BioClient
{
    public static void main(String[] args)
    {
        BufferedReader in = null;
        PrintWriter out = null;
        Socket socket = null;
        String resp = null;
        try
        {
            socket  = new Socket("127.0.0.1", 6666);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("I am client");
            resp = in.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("resp:"+resp);
    }
}
