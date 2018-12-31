package com.example.demo.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimit {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean ValidRequestRate(HttpServletRequest request) throws AccessException {
        String ip = request.getRemoteAddr();

        String key = "ddos." + ip;

        long count = stringRedisTemplate.opsForValue().increment(key);
        System.out.println(key + ": " + count);

        if(count >10){
            throw new AccessException("access too frequently ...");
        }else{
            if(count ==1)
                stringRedisTemplate.expire(key,10, TimeUnit.SECONDS);
        }

        return true;
    }
}
