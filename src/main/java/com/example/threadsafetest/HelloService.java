package com.example.threadsafetest;

import com.example.threadsafetest.people.People;
import com.example.threadsafetest.people.PeopleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class HelloService {
    final private PeopleRepository peopleRepository;

    @Transactional
    public void plusNumber() {
        boolean updated = false;
        while (!updated) {
            try {
                People people = peopleRepository.findPeopleByName("jun");
                people.setCount(people.getCount() + 1);
                peopleRepository.save(people);
                updated = true;
            } catch (OptimisticLockingFailureException e) {
                // 충돌이 발생하면 루프를 돌면서 재시도
                System.out.println("Optimistic lock exception, retrying...");
            }
        }
    }

    public int getNumber() {
        return peopleRepository.findPeopleByName("jun").getCount();
    }

}
