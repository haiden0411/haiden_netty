package com.huawei.butter;
import io.netty.util.NettyRuntime;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
/**
 * Author：胡灯
 * Date：2020-07-20 21:20
 * Description：<描述>
 */
public class NIObutterTest
{

    private static final String filepath01 = "d:/test/aa/1.txt";
    private static final String filepath02 = "d:/test/aa/2.txt";
    @Test
    public void testbuffer1() throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(filepath01);
        FileChannel ch01 = fileInputStream.getChannel();
        FileOutputStream outputStream = new FileOutputStream(filepath02);
        FileChannel ch02 = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true)
        {
            buffer.clear();
            int read = ch01.read(buffer);
            System.out.println("read:"+read);
            if (read == -1)
            {
                break;
            }
            buffer.flip();
            ch02.write(buffer);
        }
        fileInputStream.close();
        outputStream.close();
    }
    @Test
    public void testTransfrom() throws Exception{
        FileInputStream fileInputStream = new FileInputStream("d:/test/aa/a.jpg");
        FileOutputStream outputStream = new FileOutputStream("d:/test/aa/b.jpg");
        FileChannel src = fileInputStream.getChannel();
        FileChannel dest = outputStream.getChannel();
        dest.transferFrom(src,0,src.size());
        src.close();
        dest.close();
        fileInputStream.close();
        outputStream.close();
    }
    @Test
    public void testTransto() throws Exception{
        FileInputStream fileInputStream = new FileInputStream("d:/test/aa/a.jpg");
        FileOutputStream outputStream = new FileOutputStream("d:/test/aa/c.jpg");
        FileChannel src = fileInputStream.getChannel();
        FileChannel dest = outputStream.getChannel();
        src.transferTo(0,src.size(),dest);
        src.close();
        dest.close();
        fileInputStream.close();
        outputStream.close();
    }
    @Test
    public void testNIOputAndget(){
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(1000);
        buffer.putLong(9);
        buffer.putChar('胡');
        buffer.putShort((short) 4);
        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
    }

    @Test
    public void testReadonlyBuffer(){
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++)
        {
            buffer.put((byte) i);
        }
        buffer.flip();
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        while (readOnlyBuffer.hasRemaining())
        {
            System.out.println(readOnlyBuffer.get());
        }
       // readOnlyBuffer.put((byte) 100);
    }

    @Test
    public void testMapByteBuffer() throws Exception
    {
        RandomAccessFile rw = new RandomAccessFile("d:/test/aa/1.txt", "rw");
        FileChannel channel = rw.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'H');
        map.put(3, (byte) '9');
        map.put(5, (byte) 'y');
        rw.close();
    }
    @Test
    public void testProcess(){
        System.out.println(NettyRuntime.availableProcessors());
    }
}
