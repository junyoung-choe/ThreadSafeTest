package com.example.threadsafetest.board;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping()
    public Integer plusViews() {
        return boardService.plusViews(1L);
    }

    @GetMapping("/{id}")
    public Integer getViews(@PathVariable Long id) {
        return boardService.getViews(id);
    }
}
