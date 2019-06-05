package com.luxh.server;

/**
 * @Description 程序启动类
 * @Author luxiaohua
 * @Date 2019/6/5
 */
public class Main {
    public static void main(String[] args) {
        int port = 8080;
        new NettyHttpServer(port).run();
    }
}
