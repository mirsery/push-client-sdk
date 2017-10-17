package cn.szjlxh.push.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.util.CharsetUtil;

public class PushClientInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("delimiter", new DelimiterBasedFrameDecoder(1024 * 60, Unpooled.copiedBuffer("#", CharsetUtil.UTF_8),
                Unpooled.copiedBuffer("#", CharsetUtil.UTF_8)));
        pipeline.addLast("InBoundHandler", new PushInBoundHandler());
        pipeline.addLast("OutBoundHandler", new PushOutBoundHandler());
    }
}
