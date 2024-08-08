package com.example.threadsafetest.study;

import com.example.threadsafetest.people.People;
import com.example.threadsafetest.people.PeopleService;
import com.example.threadsafetest.study.Study;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    @Tag("fast")
    void create_new_study() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(50);
        });
    }

    @Test
    @DisplayName("스터디 다시 만들기")
    @Tag("slow")
    void create_new_study_again() {
        System.out.println("create");
    }

    // 반드시 1. static , 2. 리턴 타입 x , 3. private x
    @BeforeAll
    @Disabled // 전체 주석이랑 같은 역할을 한다
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    // 각 각의 테스트가 시작 or 이후에 실행되기 때문에 static일 필요는 업다
    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

}