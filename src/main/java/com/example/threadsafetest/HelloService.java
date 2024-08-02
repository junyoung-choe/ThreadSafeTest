package com.example.threadsafetest;

import com.example.threadsafetest.people.People;
import com.example.threadsafetest.people.PeopleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class HelloService {
    final private PeopleRepository peopleRepository;

    public void plusNumber() {
        People people = peopleRepository.findPeopleByName("jun");
        people.setCount(people.getCount() + 1);
    }

    public int getNumber() {
        return peopleRepository.findPeopleByName("jun").getCount();
    }

}
