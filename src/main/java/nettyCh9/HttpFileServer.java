package nettyCh9;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description: TODO
 * @Author: chris_cai@enable-ets.com
 * @Since: 2018年11月05日
 */
public class HttpFileServer {

    private static final String DEFAULT_URL = "/src";

    public void run(final int port ,final String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            sc.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                            sc.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                            sc.pipeline().addLast("http-chunker",new ChunkedWriteHandler());
                            sc.pipeline().addLast("fileServerHandler",new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture f = b.bind("127.0.0.1", port).sync();
            System.out.println("HTTP 文件目录服务器启动,网址是: " + "http://localhost:" + port + url);
            f.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new HttpFileServer().run(port, DEFAULT_URL);
    }
}
