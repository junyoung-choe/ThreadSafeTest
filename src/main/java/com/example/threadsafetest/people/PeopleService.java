package com.example.threadsafetest.people;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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
}