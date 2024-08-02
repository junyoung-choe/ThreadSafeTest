package com.example.threadsafetest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
//@Scope("prototype")
public class HelloService {
    private int number = 0;

    public synchronized void plusNumber() {
        number++;
    }

    public synchronized int getNumber() {
        return number;
    }

}
