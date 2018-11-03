package nioCh9;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @Description: TODO
 * @Author: chris_cai@enable-ets.com
 * @Since: 2018年11月03日
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request req = (Request) msg;
        if (req.getUrl().contains("czy")) {
            System.out.println("server accept client req : [ " + req.getUrl() + " ]");
            ctx.writeAndFlush(resp(req.getUrl()));
        }
    }

    private Response resp(String url) {
        return new Response(22, url);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
