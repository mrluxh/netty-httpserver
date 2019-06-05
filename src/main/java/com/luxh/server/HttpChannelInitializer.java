package com.luxh.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @Description 通道初始化
 * @Author luxiaohua
 * @Date 2019/6/5
 */
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
                .addLast(new HttpRequestDecoder())  //  请求消息解码器
                .addLast(new HttpObjectAggregator(65535)) // 将多个消息转换为单一的request或者response对象
                .addLast(new HttpResponseEncoder())  // 响应消息解码器
                .addLast(new HttpRequestHandler()); // 业务逻辑处理器
    }
}
