package com.example.threadsafetest.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Spring과 Mock을 결합하여 테스트에서 사용하수 있도록 설정한다.
@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    @Mock MemberService memberService;
    @Mock StudyRepository studyRepository;

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy() {
        //Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@test.com");

        Study study = new Study(10, "테스트");

        // TODO studyRepository Mock 객체의 save 메소드를호출 시 study를 리턴하도록 만들기.
        when(memberService.findById(any())).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        // Then
        // studyService.createNewStudy 이 메소드 내부에 정의되어있는 mock 객체의 동장을 위에 정의해놓았다.
        studyService.createNewStudy(1L, study);
        assertEquals(member.getId(), study.getOwnerId());


    }
}