package com.example.threadsafetest;

import com.example.threadsafetest.people.People;
import com.example.threadsafetest.people.PeopleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelloService {
    final private PeopleRepository peopleRepository;

    @Transactional
    public void plusNumber() {
        People people = peopleRepository.findPeopleByName("jun");
        people.setCount(people.getCount() + 5);
//        peopleRepository.save(people);
    }

    @Transactional
    public int getNumber() {
        return peopleRepository.findPeopleByName("jun").getCount();
    }

}
