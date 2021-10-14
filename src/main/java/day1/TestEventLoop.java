package day1;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;

import java.util.concurrent.TimeUnit;

public class TestEventLoop {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();//处理IO事件、普通任务、定时任务
//        DefaultEventLoop eventExecutors1 = new DefaultEventLoop();//普通任务、定时任务
        System.out.println("线程数：" + NettyRuntime.availableProcessors());
        //普通任务
        eventExecutors.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ok");
        });
        Thread.sleep(1000);
        //定时任务
        eventExecutors.scheduleWithFixedDelay(() -> {
            System.out.println("time task");
        }, 0, 1, TimeUnit.MILLISECONDS);
    }
}
