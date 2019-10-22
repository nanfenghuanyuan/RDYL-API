package com.zh.module.controller;

import com.zh.module.config.NettyConfig;
import com.zh.module.model.WebSocketClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.ImmediateEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 接受/处理/响应客户端websocke请求的核心业务处理类
 * @author 赵赫
 */
@Slf4j
public class MyWebSockeHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker webSocketServerHandshaker;
    private static final String WEB_SOCKET_URL = "ws://localhost:1606/";
    private static Map<String, WebSocketClient> allSocketClients = new ConcurrentHashMap<>();

    /**
     * 客户端与服务端创建链接的时候调用
     */

    @Override
    public void channelActive (ChannelHandlerContext context) {
        Channel incoming = context.channel();
        NettyConfig.group.add(incoming);
        log.info("Client:" + incoming.remoteAddress().toString() + "加入,当前链接数：" + NettyConfig.group.size());
    }

    /**
     * 客户端与服务端断开连接的时候调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext context) {
        Channel incoming = context.channel();
        NettyConfig.group.remove(incoming);
        allSocketClients.remove(incoming.remoteAddress().toString());
        log.info("Client:" + incoming.remoteAddress().toString() + "离开,当前链接数：" + NettyConfig.group.size());
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext context) {
        context.flush();
    }

    /**
     * 工程出现异常的时候调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable throwable) {
        Channel incoming = context.channel();
        log.info("Client:" + incoming.remoteAddress() + "异常");
        allSocketClients.remove(incoming.remoteAddress().toString());
        throwable.printStackTrace();
        context.close();
    }

    /**
     * 服务端处理客户端websocke请求的核心方法
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        //处理客户端向服务端发起的http握手请求
        if (o instanceof FullHttpRequest){
            handHttpRequest(channelHandlerContext,(FullHttpRequest) o);
            //处理websocket链接业务
        }else if (o instanceof WebSocketFrame){
            handWebSocketFrame(channelHandlerContext,(WebSocketFrame) o);
        }
    }

    /**
     * 处理客户端与服务端之间的websocket业务
     */
    private void handWebSocketFrame(ChannelHandlerContext context, WebSocketFrame webSocketFrame){
        //判断是否是关闭websocket的指令
        if (webSocketFrame instanceof CloseWebSocketFrame){
            webSocketServerHandshaker.close(context.channel(),(CloseWebSocketFrame) webSocketFrame.retain());
            return;
        }
        //判断是否是ping消息
        if (webSocketFrame instanceof PingWebSocketFrame){
            context.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
            return;
        }
        String request = ((TextWebSocketFrame) webSocketFrame ).text();
        System.out.println("服务端收到客户端的消息：" + request);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(context.channel().id() + ":" + request);
        //服务端向每个连接上来的客户端发送消息
        NettyConfig.group.writeAndFlush(textWebSocketFrame);
    }




    /**
     * 处理客户端向服务端发起http握手请求业务
     */
    private void handHttpRequest(ChannelHandlerContext context, FullHttpRequest fullHttpRequest){
        //判断是否http握手请求
        if (!fullHttpRequest.getDecoderResult().isSuccess() ||!("websocket".equals(fullHttpRequest.headers().get("Upgrade")))){
            sendHttpResponse(context,fullHttpRequest, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory(WEB_SOCKET_URL,null,false);
        webSocketServerHandshaker = webSocketServerHandshakerFactory.newHandshaker(fullHttpRequest);
        if (webSocketServerHandshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(context.channel());
        }else{
            webSocketServerHandshaker.handshake(context.channel(),fullHttpRequest);
        }
    }

    /**
     * 服务端想客户端发送响应消息
     */
    private void sendHttpResponse(ChannelHandlerContext context, FullHttpRequest fullHttpRequest, DefaultFullHttpResponse defaultFullHttpResponse){
        if (defaultFullHttpResponse.getStatus().code() != 200){
            ByteBuf buf = Unpooled.copiedBuffer(defaultFullHttpResponse.getStatus().toString(), CharsetUtil.UTF_8);
            defaultFullHttpResponse.content().writeBytes(buf);
            buf.release();
        }
        //服务端向客户端发送数据
        ChannelFuture future = context.channel().writeAndFlush(defaultFullHttpResponse);
        if (defaultFullHttpResponse.getStatus().code() !=200){
            future.addListener(ChannelFutureListener.CLOSE);
        }

    }

}
