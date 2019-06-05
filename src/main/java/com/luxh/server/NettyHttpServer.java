package com.luxh.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description netty http server
 * @Author luxiaohua
 * @Date 2019/6/5
 */
public class NettyHttpServer {

    // 服务端口
    private int port;

    public NettyHttpServer(int port) {
        this.port = port;
    }


    public void run() {
        /**
         * 创建两组独立的Reactor线程池
         * bossGroup用于接收客户端TCP连接
         * workerGroup用于处理IO读写操作
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 服务端引导启动器，负责服务端基本信息配置，完成服务端启动
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 1024);//标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度

            ChannelFuture f = serverBootstrap.bind(port).sync();
            System.out.println(String.format("netty http server启动成功...访问地址:http://127.0.0.1:%d", port));
            f.channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }






    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
