package com.chx.chat.netty.handler;

import com.chx.chat.netty.service.WebSocketService;
import com.chx.chat.netty.util.WebSocketUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月06日
 */
@Slf4j
@Configuration
@ChannelHandler.Sharable
public class HttpHeadersHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Autowired
    private WebSocketService webSocketService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        String uri = request.uri();
        Long uid = WebSocketUtil.resovlerUri(uri);

        webSocketService.put(uid,ctx.channel());
        log.info("uid: " + uid);
        // 如果该用户有离线消息
        if (webSocketService.containsKey(uid)) {
            log.info("用户存在离线消息...");
            String offLineMessage = webSocketService.getOffLineMessage(uid);

            ctx.channel().writeAndFlush(new TextWebSocketFrame("offLineMessage"));
            log.info("用户离线消息：" + offLineMessage);
        }
        request.setUri("/ws");

        ctx.fireChannelRead(request.retain());
    }

}
