package dev.be.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final EmailService emailService;

    public void asyncCall_1(){
        System.out.println("[asyncCall_1] : "+ Thread.currentThread().getName());
        // case1. bean 주입을 받아 사용하는 async 코드 -> 비동기 처리 됨
        emailService.sendMail(); // sub thread에 위임
        emailService.sendMailWithCustomThreadPool(); // sub thread에 위임
    }

    public void asyncCall_2(){
        System.out.println("[asyncCall_2] : "+ Thread.currentThread().getName());
        // instance 선언 (bean 주입x) -> 비동기 처리 x = spring의 도움을 못받아서
        EmailService emailService = new EmailService();
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }

    public void asyncCall_3(){
        System.out.println("[asyncCall_3] : "+ Thread.currentThread().getName());
        // 내부 메소드를 비동기로 선언 -> 비동기 처리 x
        sendMail();
    }

    @Async // 어노테이션 없는 것과 동일하게 동작
    public void sendMail(){
        System.out.println("[sendMail] : "+Thread.currentThread().getName());
    }
}
