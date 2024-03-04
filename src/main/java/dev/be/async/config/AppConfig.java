package dev.be.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class AppConfig {

    // thread pool 정의
    @Bean(name = "defaultTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor defaultTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(200);
        executor.setMaxPoolSize(300);
        executor.setKeepAliveSeconds(10);
        return executor;
    }

    // thread pool 정의
    @Bean(name = "messageTaskExecutor",destroyMethod = "shutdown"
    )
    public ThreadPoolTaskExecutor messageTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(200);
        executor.setMaxPoolSize(300);
        executor.setKeepAliveSeconds(10);
        return executor;
    }


}
