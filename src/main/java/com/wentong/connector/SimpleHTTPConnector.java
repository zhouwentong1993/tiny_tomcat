package com.wentong.connector;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
public class SimpleHTTPConnector implements HttpHandler, AutoCloseable {

    public static void main(String[] args) throws Exception {
        try (SimpleHTTPConnector simpleHTTPConnector = new SimpleHTTPConnector("localhost", 8080)) {
            for (;;) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    private String host;
    private int port;
    private HttpServer httpServer;

    public SimpleHTTPConnector(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.httpServer = HttpServer.create(new InetSocketAddress(this.host, this.port), 0, "/", this);
        httpServer.start();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        log.info("handle request");
        log.info("request method: {}", exchange.getRequestMethod());
        log.info("request uri: {}", exchange.getRequestURI());
        log.info("request protocol: {}", exchange.getProtocol());
        log.info("request headers: {}", exchange.getRequestHeaders());
        log.info("request body: {}", exchange.getRequestBody());
        // 输出响应的Header:
        Headers respHeaders = exchange.getResponseHeaders();
        respHeaders.set("Content-Type", "text/html; charset=utf-8");
        respHeaders.set("Cache-Control", "no-cache");
        // 设置200响应:
        exchange.sendResponseHeaders(200, 0);
        // 输出响应的内容:
        String s = "<h1>Hello, world.</h1><p>" + LocalDateTime.now().withNano(0) + "</p>";
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(s.getBytes(StandardCharsets.UTF_8));
        }

    }

    @Override
    public void close() throws Exception {
        // 等三秒之后再关闭，简单的优雅关闭
        this.httpServer.stop(3);

    }
}
