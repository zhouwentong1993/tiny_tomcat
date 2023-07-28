package com.wentong.connector.servlet;

import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpExchangeResponse {

    Headers getResponseHeaders();
    void sendResponseHeaders(int rCode, long responseLength) throws IOException;
    OutputStream getResponseBody();

}
