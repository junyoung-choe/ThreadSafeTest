package com.example.threadsafetest.people;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface PeopleRepository  extends JpaRepository<People, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    People findPeopleByName(String name);
}
