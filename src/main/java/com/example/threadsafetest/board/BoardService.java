package com.example.threadsafetest.board;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.threadsafetest.config.redis.RedissonConfig.FEED_VIEW_COUNT_PREFIX;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final RedisTemplate<String, Long> redisTemplate;

//    public Integer plusViews(Long id, Long views) { return boardRepository.plusBoardViews(id); }

    public Integer plusViews(Long id) {
        String redisKey = FEED_VIEW_COUNT_PREFIX + id;
        Long increment = redisTemplate.opsForValue().increment(redisKey, 5L);
        if(increment == null) return 0;
        return increment.intValue();
    }
    public Integer getViews(Long id) {
        return boardRepository.findBoardById(id).getViews();
    }


}
