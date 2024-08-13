package com.example.threadsafetest.people;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository  extends JpaRepository<People, Long> {
    People findPeopleByName(String name);
}
