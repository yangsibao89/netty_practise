package com.shengsiyuan.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by yangsibao on 2018/10/21.
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {//任何客户端都会调用该方法
        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {//遍历组中channel
            if (channel != ch) {//channel为当前连接的客户端，ch为遍历的成员。
                ch.writeAndFlush(channel.remoteAddress() + " 发送的消息 " + msg + "\n");//注意是channel不是ch，获取的是远端的
            } else {//否则为自己
                ch.writeAndFlush(" 【自己】 " + msg + "\n");
            }
        });

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {//连接已建立
        Channel channel = ctx.channel();//获取channel

        channelGroup.writeAndFlush("【服务器】 -- " + channel.remoteAddress() + " 加入\n");//向组里所有成员广播消息
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("【服务器】 -- " + channel.remoteAddress() + "离开\n");
        //channelGroup.remove(channel);//把channel移除组,可省略,netty会自动调用

        System.out.println(channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        System.out.println(channel.remoteAddress() + " 上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        System.out.println(channel.remoteAddress() + " 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
