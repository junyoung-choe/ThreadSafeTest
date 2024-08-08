package com.example.threadsafetest.people;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @ActiveProfiles("test") // application-test.properties를 사용하도록 설정

@SpringBootTest
@AutoConfigureMockMvc
public class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void makeReserve() throws Exception {
        int numRequests = 100;
        String responseBody = null;

        for (int i = 0; i < numRequests; i++) {
            // 각 요청을 순차적으로 수행
            responseBody = mockMvc.perform(MockMvcRequestBuilders.get("/people"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
        }

        assertEquals("500", responseBody);
    }

    @Test
    void makeSameTimeReserve() throws InterruptedException, ExecutionException {
        int numThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            tasks.add(() -> {
                return mockMvc.perform(MockMvcRequestBuilders.get("/people"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
            });
        }

        List<Future<String>> futures = executorService.invokeAll(tasks);

        int responseBody = 0;

        for (int i = 0; i < numThreads; i++) {
            Future<String> nowFuture = futures.get(i);
            responseBody = Math.max(responseBody, Integer.parseInt(nowFuture.get()));
        }

        assertEquals("500", String.valueOf(responseBody));  // 검증

        executorService.shutdown();
    }
}