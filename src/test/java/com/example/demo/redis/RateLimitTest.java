package com.example.demo.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.AccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.concurrent.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RateLimitTest {

    @Resource
    private RateLimit rateLimit;

    @Test
    public void validRequestRate() throws AccessException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("1.1.1.1");
        when(request.getParameter("username")).thenReturn("me");

        boolean result = rateLimit.ValidRequestRate(request);

        Assert.isTrue(result, "true");
    }

    @Test
    public void testConcurrentRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("2.2.2.2");

        // 并发线程数
        int count = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        ExecutorService executorService = Executors.newCachedThreadPool();
        int n =0;
        for(int i=0; i< count; i++){
            executorService.execute(()->{
                try {
                    cyclicBarrier.await();

                    boolean result = rateLimit.ValidRequestRate(request);
                    System.out.println(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (AccessException e){
                    e.printStackTrace();
                }
            });
        }

        // 关闭线程池
        executorService.shutdown();

        try {
            //等待直到所有任务完成
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}