package com.example.threadsafetest.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Query("update Board b set b.views = b.views + 5 where b.id = :id")
    Integer plusBoardView(Long id);

    Board findBoardById (Long id);
}
