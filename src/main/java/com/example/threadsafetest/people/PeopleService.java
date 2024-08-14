package com.example.threadsafetest.people;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeopleService {
    final private PeopleRepository peopleRepository;
    final private RedissonClient redissonClient;

    long waitTime = 5L;
    long leaseTime = 3L;
    TimeUnit timeUnit = TimeUnit.SECONDS;

    @Transactional
    public int plusNumber() {
        // 락 이름 배정
        String lockName = "jun";
        RLock rLock = redissonClient.getLock(lockName);

        try {
            boolean available = rLock.tryLock(waitTime, leaseTime, timeUnit);
            if (!available) {
                throw new RuntimeException("Unable to acquire lock");
            }

            // 트랜잭션 커밋 후 락 해제를 위해 등록
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    rLock.unlock();
                    log.info("락 반납했다 ~ ");
                }
            });
            log.info("락 획득했다 ~ ");
            // === 락 획득 후 로직 수행 ===
            People people = peopleRepository.findPeopleByName("jun");
            people.setCount(people.getCount() + 5);
            return people.getCount();
            // === 로직 수행 완료 ===

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 해당하는 id의 캐시를 삭제한다.
    // @CacheEvict(cacheNames = "COUNT", key = "#name", cacheManager = "cacheManager")

    // 해당하는 id의 캐시가 없다면 "아래 로직 실행 후" 등록한다.
    // 해당하는 id의 캐시가 있다면 가져온다.
    @Cacheable(cacheNames = "COUNT", key = "#name", condition = "#name != null", cacheManager = "cacheManager")
    public int getCount(String name) {
        return peopleRepository.findPeopleByName(name).getCount();
    }
}