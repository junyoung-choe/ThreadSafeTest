package com.example.threadsafetest.people;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeopleService {
    final private PeopleRepository peopleRepository;

    @Transactional
    public void plusNumber() {
        People people = peopleRepository.findPeopleByName("jun");
        people.setCount(people.getCount() + 5);
//        peopleRepository.save(people);
    }

    @Transactional(readOnly = true)
    public int getNumber() {
        return peopleRepository.findPeopleByNameWithoutLock("jun").getCount();
    }

}
