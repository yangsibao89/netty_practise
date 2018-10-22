package com.shengsiyuan.netty.fifthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * Created by yangsibao on 2018/10/22.
 */
public class TextWebsocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println(" 收到消息 " + msg.text());

//        ctx.channel().writeAndFlush(" 服务器时间 " + LocalDateTime.now());
        ctx.channel().writeAndFlush(new TextWebSocketFrame(" 服务器时间 " + LocalDateTime.now()));//websocket一定要用TextWebSocketFrame
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" handlerAdded: " + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" handlerRemoved: " + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(" 异常发生 ");
        ctx.close();
    }
}
