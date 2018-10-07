package com.vertx.test;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VertxDemo extends AbstractVerticle
{

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static HttpClient client = null;

    @Override
    public void start() throws Exception
    {
        Router router = Router.router(vertx);

        router.route().handler(routingContext -> {
            printMsg(": got a request");
            routingContext.response().putHeader("content-type", "test/html").end("Hello kitty!");
        });

        // TODO create server
        HttpServerOptions serverOptions = new HttpServerOptions();
        serverOptions.setUsePooledBuffers(true);
        // 1s
        serverOptions.setIdleTimeout(1);
        vertx.createHttpServer(serverOptions).requestHandler(router::accept).listen(9527);
        printMsg(": port 9527 listened");


        // TODO create client
        HttpClientOptions clientOptions = new HttpClientOptions();
        clientOptions.setMaxPoolSize(5);
        clientOptions.setIdleTimeout(1);
        clientOptions.setKeepAlive(true);
        client = vertx.createHttpClient(clientOptions);
    }

    public static HttpClient getClient()
    {
        return client;
    }

    private void printMsg(String msg)
    {
        System.out.println(dateFormat.format(new Date()) + msg);
    }
}
