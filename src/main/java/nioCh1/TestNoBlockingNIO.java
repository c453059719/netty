package nioCh1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


import org.junit.Test;

/**
 * @Description: TODO
 * @Author: chris_cai@enable-ets.com
 * @Since: 2018年11月02日
 */
public class TestNoBlockingNIO {

    @Test
    public void server() throws IOException {
        //1.获取通道并设置为异步
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        //2.获取选择器
        Selector selector = Selector.open();
        //3.获取缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //4.绑定端口
        ssc.bind(new InetSocketAddress(8080));
        //5.将通道注册到选择器上
        ssc.register(selector, SelectionKey.OP_ACCEPT);



        //6.监听选择器
        while (selector.select() > 0) {
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();

                //7.如果有新连接,则注册读事件
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    InetSocketAddress localAddress = (InetSocketAddress) sc.getLocalAddress();
                    System.out.println("有新连接接入:" + localAddress.getHostName() + ":" + localAddress.getPort());
                }

                //8.如果有读事件
                if (key.isReadable()) {
                    SocketChannel sc =(SocketChannel) key.channel();
                    int len = 0;
                    if ((len = sc.read(buffer)) > 0) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,len));
                        buffer.clear();
                    }
                }
                it.remove();
            }
        }
    }

    @Test
    public void client() throws IOException {
        //1.获取通道,并设置异步
        SocketChannel sc = SocketChannel.open();

        //2.获取选择器
        Selector selector = Selector.open();

        //3.连接服务器
        sc.connect(new InetSocketAddress("127.0.0.1", 8080));

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("hello nio".getBytes());
        buffer.flip();
        sc.write(buffer);

        sc.close();
    }
}
