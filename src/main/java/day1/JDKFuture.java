package day1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class JDKFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future = executorService.submit(() -> {
            Thread.sleep(1000);
            log.info("执行计算");
            return 50;
        });
        log.info("等待结果");
        Integer result = future.get();
        log.info("result:{}", result);

    }
}
