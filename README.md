# Spring Async
- Async한 통신
- 실시간성 응답을 필요로 하지 않는 상황
- ex) notification, email 전송

→ **main thread가 task를 처리하는 게 아니라 sub thread에게 task를 위임하는 행위**

- 그렇다면 sub thread는 어떻게 생성하고, 어떻게 관리하는가?

→ spring에서 비동기 프로그래밍을 하기 위해선 thread pool을 정의해야 함

- java에서는 thread pool을 생성해서 async 작업 처리

<br>


## Thread Pool

thread가 여러개 있는 pool

- core pool size : 해당 스레드풀에 스레드를 최소 몇 개 가지고 있는지 정하는 옵션
- maximum pool size : 스레드를 할당할 최대 갯수
- keep alive time : 작업하지 않고 놀고 있는 쓰레드(=비활성 쓰레드)가 유지될 최대 시간
- unit : 시간 설정 단위 ex) 초, 분
- work queue : 실행할 작업이 모인 큐

→ 처음엔 core pool size 만큼 생성, 그 이후에 요청이 더 들어오면 work queue에 작업 쌓임

→ work queue 크기만큼 작업이 쌓이면(큐가 다 차면) maximum 만큼 스레드 생성



<br>

### thread pool 생성시 주의할 점

- core pool size값을 너무 크게 설정할 경우 side effect
    
    해당 수만큼 자원을 점유하므로, thread를 잘 사용하지 않더라도 자원을 점유해 오버헤드 발생 가능
    
- IllegalArgumentException 발생
    - corePoolSize <0
    - keepAliveTime <0
    - maximumPoolSize<0
    - maximumPoolSize< corePoolSize
- NullPointerException 발생
    - work queue가 null인 경우
    
![image](https://github.com/zeunxx/fastcampus-10Project-6-AsyncProgramming/assets/81572478/bba30239-5b53-4102-98df-93348e6046ff)
![image](https://github.com/zeunxx/fastcampus-10Project-6-AsyncProgramming/assets/81572478/aef5c89c-cd46-46ce-b99d-f43edeb68c00)



<Br>

## ✅ Spring Async 정리
- spring에서 async를 사용하려면 bean을 주입받아서 사용해야 함
    

    ![image](https://github.com/zeunxx/fastcampus-10Project-6-AsyncProgramming/assets/81572478/642588f4-daf1-4062-8a8e-e4c8813c9ebf)

    spring에서 비동기 처리를 위해 해당 빈의 비동기 로직을 프록시로 wrapping해서 주기때문
    
    - spring AOP
    - 
        spring context에 등록된 async bean(Email Service)이 호출되면 spring이 개입해서 해당 빈을 프록시로 wrappig한다.
        
        bean으로 등록되는 시점에 프록시 객체화를 한다. 호출한 객체(caller)는 AOP에 의해 만들어진 프록시 객체화된 Async bean을 참조하게 된다.
        
        - 따라서, private으로 지정되어 있으면 AOP가 프록시 객체로 만들기 위해 해당 메소드에 접근하지 못함
        - 자가 호출/내부 호출의 경우 프록시를 거치지 않고 직접 메소드를 호출하므로 Async 동작 x
        
- 비동기 처리는 요청에 대한 결과를 기다리지 않아 작업 병렬처리 가능
- **@Async**는 스프링에서 제공하는 비동기 처리 어노테이션
- `SimpleAsyncTaskExecutor` 는 스레드 풀 방식이 아니므로 스레드 풀 방식의 Executor 사용!
    - pooling을 전혀 하지않고 계속 스레드를 생성/해제
- @Async를 사용할땐 private 금지, 자가호출 금지
