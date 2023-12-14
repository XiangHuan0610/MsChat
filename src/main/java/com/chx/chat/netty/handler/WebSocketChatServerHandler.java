package com.chx.chat.netty.handler;


import com.chx.chat.constant.MessageCodeConstant;
import com.chx.chat.netty.entity.Message;
import com.chx.chat.netty.entity.MessageContent;
import com.chx.chat.netty.service.WebSocketService;
import com.chx.chat.netty.util.JsonUtil;
import com.chx.chat.utils.TimeUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


@Slf4j
@Configuration
@ChannelHandler.Sharable
public class WebSocketChatServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private ConcurrentHashMap map = new ConcurrentHashMap<String, Channel>();

    @Autowired
    private WebSocketService webSocketService;



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("CLIENT : " + incoming.id()  + "-" + incoming.remoteAddress() + "上线");
        incoming.writeAndFlush(new TextWebSocketFrame("sang"));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        Message message = JsonUtil.parse(msg.text(), Message.class);
        message.getMsg().setSendTime(TimeUtil.now());
        if (message.getAck().equals(MessageCodeConstant.MESSAGE_SUCCESS)){
            // 如果消费者回复200，则表示收到消息。
            webSocketService.del200Message(message.getMsgId());
        }else if(message.getAck().equals(MessageCodeConstant.MESSAGE_ERROR)){
            // 如果消费者回复500,则表示消息有问题
            webSocketService.send(message);
        }else{
            webSocketService.send(message);
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接，通道关闭！");

    }
}