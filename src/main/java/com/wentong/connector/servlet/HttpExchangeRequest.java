package com.wentong.connector.servlet;

import java.net.URI;

public interface HttpExchangeRequest {

    String getRequestMethod();
    URI getRequestURI();

}
