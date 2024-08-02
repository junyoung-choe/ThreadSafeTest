package com.example.threadsafetest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("hello")
@RequiredArgsConstructor
public class HelloController {


    final private HelloService helloService;

    @GetMapping()
    public Integer hello() {

        for (int i = 0; i < 5; i++) {
            helloService.plusNumber();
        }


        return helloService.getNumber();
    }
}
