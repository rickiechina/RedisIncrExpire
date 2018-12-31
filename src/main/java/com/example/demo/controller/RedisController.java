package com.example.demo.controller;

import com.example.demo.redis.RateLimit;
import org.springframework.expression.AccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class RedisController {
    @Resource
    private RateLimit rateLimit;

    @RequestMapping("/hello")
    public String concurrentRequest(HttpServletRequest request) throws AccessException {

        if(rateLimit.ValidRequestRate(request))
            return "success";

        return "refused!";
    }
}
