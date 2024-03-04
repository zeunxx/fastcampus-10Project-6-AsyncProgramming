package dev.be.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    // @Async 어노테이션 사용시에는 public으로 해야 함
    @Async("defaultTaskExecutor")
    public void sendMail(){
        System.out.println("[sendMail] : "+Thread.currentThread().getName());
    }

    @Async("messageTaskExecutor")
    public void sendMailWithCustomThreadPool(){
        System.out.println("[sendMailWithCustomThreadPool] : "+Thread.currentThread().getName());
    }
}
