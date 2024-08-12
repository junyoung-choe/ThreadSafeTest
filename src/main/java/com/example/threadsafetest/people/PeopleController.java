package com.example.threadsafetest.people;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("people")
@RequiredArgsConstructor
public class PeopleController {


    final private PeopleService peopleService;

    @GetMapping()
    public Integer reserve() {
        return peopleService.plusNumber();
//        return peopleService.getNumber();
    }
}
