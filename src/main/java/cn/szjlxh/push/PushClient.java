package cn.szjlxh.push;

import cn.szjlxh.push.listener.Callback;
import cn.szjlxh.push.listener.PublishListener;
import cn.szjlxh.push.listener.PushClientInitializer;
import cn.szjlxh.push.message.PushMsg;
import cn.szjlxh.push.util.PublishUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PushClient {

    private static Logger log = LoggerFactory.getLogger(PushClient.class);

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private EventLoopGroup group;
    private static PushClient pushClient;

    private static String url;
    private static int port;
    private static Channel channel;
    private static long timeOut;

    private ChannelInitializer channelInitializer;

    public PushClient(final String url, final int port, long timeOut) {

        pushClient = this;
        group = new NioEventLoopGroup();

        this.url = url;
        this.port = port;
        this.timeOut = timeOut;

        this.channelInitializer = new PushClientInitializer();

        Bootstrap b = new Bootstrap();

        b.group(group).channel(NioSocketChannel.class).handler(channelInitializer);

        final ChannelFuture future = b.connect(new InetSocketAddress(url, port));

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (future.isSuccess()) {
                    channel = channelFuture.channel();
                    log.info("push-client connect to server " + url + " at port: {} is ok !", port);
                } else {
                    log.info("push-client connect to server " + url + " at port: {} is failed !", port);
                }
            }
        });

        try {
            future.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static PushClient getPushClient() {
        return pushClient;
    }

    public Bootstrap createBootStrap(final Bootstrap bootstrap, final EventLoopGroup eventLoop) {
        if (bootstrap != null) {
            bootstrap.group(eventLoop).channel(NioSocketChannel.class).handler(channelInitializer);

            final ChannelFuture future = bootstrap.connect(new InetSocketAddress(url, port));

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (future.isSuccess()) {
                        channel = channelFuture.channel();
                        log.info("push-client connect to server " + url + " at port: {} is ok !", port);
                    } else {
                        log.info("push-client connect to server " + url + " at port: {} is failed !", port);
                        eventLoop.schedule(new Runnable() {
                            @Override
                            public void run() {
                                log.info("reconnect push-server .....");
                                createBootStrap(new Bootstrap(), eventLoop);
                            }
                        }, 30L, TimeUnit.SECONDS);
                    }
                }
            });

            future.syncUninterruptibly();
        }
        return bootstrap;
    }

    public void sendMessage(final PushMsg msg, final Callback callback) {
        final int msgId = PublishUtil.getMsgId();
        msg.setId(msgId);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush("#" + msg + "#");
                PublishUtil.addPublishList(new PublishListener(callback, msgId,timeOut));
            }
        });
    }

    /**
     * Stop server
     */
    public void stop() {
        group.shutdownGracefully().syncUninterruptibly();

        log.info("SocketIoTserver stopped");
    }


}
