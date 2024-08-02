package com.example.threadsafetest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
//@Scope("prototype")
public class HelloService {
    ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();

    // 초기 값 설정
    public HelloService() {
        concurrentHashMap.put("number", 0);
    }

    public void plusNumber() {
        concurrentHashMap.computeIfPresent("number", (Key, oldValue) -> oldValue + 1);
    }

    public int getNumber() {
        return concurrentHashMap.get("number");
    }

}
