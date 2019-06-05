package com.luxh.server;

import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

/**
 * @Description 业务处理接收到的数据
 * @Author luxiaohua
 * @Date 2019/6/5
 */
public class HttpRequestHandler extends ChannelInboundHandlerAdapter {

    /**
     * 每当有数据到达，此方法会被调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            System.out.println(String.format("uri is: %s", uri));

            System.out.println(String.format("请求参数：%s", new Gson().toJson(RequestUtils.getParams(request))));

            FullHttpResponse response = buildResponse("嘿, netty.");
            ChannelFuture channelFuture = ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

        }else {
            FullHttpResponse response = buildResponse("未处理的请求.");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    private FullHttpResponse buildResponse(String data) throws Exception{
        Resp resp = Resp.ok(data);
        Gson gson = new Gson();
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(gson.toJson(resp).getBytes("UTF-8")));
        response.headers().add("Content-Type", "application/json;charset=UTF-8");
        return response;

    }

}
