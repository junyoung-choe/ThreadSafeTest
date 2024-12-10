package com.example.threadsafetest.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public Integer plusViews(Long id) {
        return boardRepository.plusBoardView(id);
    }

    public Integer getViews(Long id) {
        return boardRepository.findBoardById(id).getViews();
    }
}
