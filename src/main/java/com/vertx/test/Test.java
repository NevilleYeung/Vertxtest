package com.vertx.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test
{
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args)
    {
        Runner.runExample(VertxDemo.class);

        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }


        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
        threadPool.scheduleAtFixedRate(() -> {
            for (int i = 0; i < 2; i++)
            {
                try
                {
                    printMsg(": No." + i + " begin to send msg =======");
                    VertxDemo.getClient().getNow(9527, "localhost", "/", rsp -> {
                       printMsg(": got response " + rsp.statusCode() + " with protocol " + rsp.version());
                       rsp.bodyHandler(body -> printMsg(": got data " + body.toString("ISO-8859-1")));
                    });

                    printMsg(": No." + i + " end =======");
                }
                catch (Throwable t)
                {
                    t.printStackTrace();
                }
            }

        }, 1000, 1000, TimeUnit.MILLISECONDS);

    }



    private static void printMsg(String msg)
    {
        System.out.println(dateFormat.format(new Date()) + msg);
    }
}
