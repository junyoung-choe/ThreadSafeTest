package com.example.threadsafetest.people;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PeopleRepository  extends JpaRepository<People, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    People findPeopleByName(String name);

    @Query("SELECT p FROM People p WHERE p.name = :name")
    People findPeopleByNameWithoutLock(@Param("name") String name);
}
