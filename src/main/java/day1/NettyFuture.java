package day1;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class NettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        EventLoop eventLoop = nioEventLoopGroup.next();
        Future<Integer> future = eventLoop.submit(() -> {
            log.info("执行计算");
            Thread.sleep(1000);
            return 80;
        });
        //同步
//        log.info("等待结果");
//        Integer result = future.get();
//        log.info("result {}", result);
        //异步
        future.addListener(future1 -> log.info("接收结果：{}", future1.getNow()));

    }
}
