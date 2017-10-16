package cn.szjlxh.push.listener;

import cn.szjlxh.push.PushOutBoundHandler;
import cn.szjlxh.push.handler.PushInBoundHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class PushClientInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("InBoundHandler",new PushInBoundHandler());
        pipeline.addLast("OutBoundHandler",new PushOutBoundHandler());
    }
}
