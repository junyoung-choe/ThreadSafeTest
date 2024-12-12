package com.example.threadsafetest.config.schedule;

import com.example.threadsafetest.board.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.example.threadsafetest.config.redis.RedissonConfig.FEED_VIEW_COUNT_PREFIX;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final BoardRepository boardRepository;
    private final RedisTemplate<String, Long> redisTemplate;

    // every 1 minute
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void applyViewsToDb() {
        Set<String> keys = redisTemplate.keys(FEED_VIEW_COUNT_PREFIX + "*");
        if (keys.isEmpty()) { // redis에 존재하는 모든 조회수를 가져온다.
            return;
        }

        // 가져온 조회수를 DB에 반영 ( redis to DB )
        keys.forEach(redisKey -> {
            Long boardId = Long.parseLong(redisKey.replace(FEED_VIEW_COUNT_PREFIX, ""));
            long ViewsCount = Optional.ofNullable(redisTemplate.opsForValue().get(redisKey))
                    .orElse(0L);
            if (ViewsCount > 0) { // 0 이상의 조회수가 쌓인 경우 동기화
                syncViewCount(redisKey, boardId, ViewsCount);
            }
        });
    }

    // DB 접근
    private void syncViewCount(String redisKey, Long boardId, long ViewsCount) {
        Integer i = boardRepository.plusBoardViews(boardId, ViewsCount);// DB 호출
        redisTemplate.opsForValue().set(redisKey, 0L);
    }
}
