package cn.szjlxh.push.handler;

import cn.szjlxh.push.PushClient;
import cn.szjlxh.push.listener.Callback;
import cn.szjlxh.push.message.ResponseMsg;
import cn.szjlxh.push.util.PublishUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class PushInBoundHandler extends ChannelInboundHandlerAdapter {

    private static Logger log = LoggerFactory.getLogger(PushClient.class);
    private static Gson gson = new GsonBuilder().create();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String message = byteBuf.toString(CharsetUtil.UTF_8);
        ResponseMsg responseMsg = gson.fromJson(message, ResponseMsg.class);
        Callback callback = PublishUtil.getCallBack(responseMsg.getId());
        if (callback != null) {
            if ("200".equals(responseMsg.getStatus())) {
                callback.onSuccess(responseMsg);
            } else {
                callback.onError(responseMsg);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("enter inactive function push-client. . .");
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                PushClient.getPushClient().createBootStrap(new Bootstrap(), eventLoop);
                log.info("reconnect push-server");
            }
        }, 10L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }
}
