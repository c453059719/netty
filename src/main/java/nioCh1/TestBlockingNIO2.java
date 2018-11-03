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
public class TestBlockingNIO2 {

    @Test
    public void client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] bytes = "hello nio".getBytes();
        byteBuffer.put(bytes);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();

        ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
        while ( socketChannel.read(byteBuffer2) !=-1) {
            byteBuffer2.flip();
            System.out.println(new String(byteBuffer2.array()));
            byteBuffer2.clear();
        }
        socketChannel.close();
    }

    @Test
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        SocketChannel socketChannel = serverSocketChannel.accept();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while ( socketChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            String req = new String(byteBuffer.array());
            System.out.println(req);
        }

        byte[] bytes = "nio yes".getBytes();
        byteBuffer.put(bytes);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        socketChannel.close();
        serverSocketChannel.close();
    }
}
