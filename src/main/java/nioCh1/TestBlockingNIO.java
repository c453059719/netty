package nioCh1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.junit.Test;

/**
 * @Description: TODO
 * @Author: chris_cai@enable-ets.com
 * @Since: 2018年11月02日
 */
public class TestBlockingNIO {

    //客户端
    @Test
    public void client() throws IOException {
        //1.获取通道
        SocketChannel socketChannel =
                SocketChannel.open(new InetSocketAddress("127.0.0.1",8080));

        //2.分配制定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //3.写入数据
        byteBuffer.put("hello nio".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        //4.关闭通道
        socketChannel.close();
    }

    //服务端
    @Test
    public void server() throws IOException {
        //1.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2.绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8080));

        //3.获取客户端连接通道
        SocketChannel socketChannel = serverSocketChannel.accept();

        //4.读取客户端数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = socketChannel.read(byteBuffer);
        if (read > 0) {
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array()));
        }
        //5.关闭通道
        socketChannel.close();
        serverSocketChannel.close();
    }
}
