package day1;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class NettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建EvenLoop对象
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventExecutors.next());
        new Thread(() -> {
            log.info("开始计算");
            try {
                int i = 1 / 0;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
            promise.setSuccess(100);
        }).start();

        log.info("等待结果");
        log.info("result {}", promise.getNow());
        Thread.sleep(2000);
        log.info("result {}", promise.getNow());
    }
}
