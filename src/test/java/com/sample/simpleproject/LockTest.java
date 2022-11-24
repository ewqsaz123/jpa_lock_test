package com.sample.simpleproject;

import com.sample.simpleproject.entity.CustomerEntity;
import com.sample.simpleproject.repository.CustomerRepository;
import com.sample.simpleproject.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class LockTest {

    @Autowired
    public CustomerService customerService;

    @Autowired
    public CustomerRepository customerRepository;

    @BeforeAll
    static void 데이터셋팅(@Autowired CustomerRepository repository){
        CustomerEntity e1 = new CustomerEntity();
        e1.setName("일일일");
        e1.setMileage(100);

        repository.save(e1);
    }

    void 마일리지점수1감소_병렬처리_NONE(){
       //병렬처리로 실행할 스레드풀을 생성
        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for(int i=0; i<2;i++){
            executorService.execute(()->{
                System.out.println("==========================>스레드 실행 시작");
                customerService.decreaseNumber(1);  //마일리지 점수 -1
                latch.countDown();
            });
        }
    }

    void 마일리지점수1감소_병렬처리_PESSIMISTIC_WRITE(){
        //병렬처리로 실행할 스레드풀을 생성
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for(int i=0; i<5;i++){
            executorService.execute(()->{
                System.out.println("==========================>스레드 실행 시작");
                customerService.decreaseNumberPessimistic(1);  //마일리지 점수 -1
                latch.countDown();
            });
        }
    }

    @Test
    @DisplayName("낙관적락_NONE : 스레드1과 2에서 동시에 마일리지 수정 -> 예외 발생, 마일리지 점수는 99")
    void 낙관적락_NONE_업데이트_테스트() throws InterruptedException{
        마일리지점수1감소_병렬처리_NONE();
        Thread.sleep(1000);
        Assertions.assertEquals(99, customerService.find(1).getMileage());
    }

    @Test
    @DisplayName("낙관적락_OPTIMISTIC : 스레드1(이름을 홍길동으로 변경)을 1초 동안 실행+스레드2(스레드 시작과 끝에 엔티티를 조회)를 3초간 실행 -> 스레드2의 두개의 쿼리결과가 같지않음 ")
    void 낙관적락_OPTIMISTIC() throws InterruptedException{
        //병렬처리로 실행할 스레드풀을 생성
        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //스레드1
        executorService.execute(()->{
            System.out.println("==========================>스레드1시작");
            customerService.changeName(1, "홍길동");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("==========================>스레드1종료");

            latch.countDown();
        });

        //스레드2
        executorService.execute(()->{
            CustomerEntity entity = customerService.find(1);
            System.out.println("==========================>스레드2시작 : 이름 = "+ entity.getName()+", 버전 = " + entity.getVersion());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            CustomerEntity entity2 = customerService.find(1);
            System.out.println("==========================>스레드2종료 : 이름 = "+ entity2.getName()+", 버전 = " + entity2.getVersion());
            Assertions.assertNotEquals(entity.getName(), entity2.getName());
            latch.countDown();
        });
        latch.await();
    }

    @Test
    @DisplayName("비관적락_PESSIMISTIC_WRIE : 스레드 5개에서 동시에 마일리지 -1 -> 마일리지 점수는 95")
    void 비관적락_PESSIMISTIC_WRITE() throws InterruptedException{
        마일리지점수1감소_병렬처리_PESSIMISTIC_WRITE();
        Thread.sleep(1000);
        Assertions.assertEquals(95, customerService.find(1).getMileage());
    }

    @Test
    @DisplayName("비관적락_PESSIMISTIC_FORCE_INCREMENT : 엔티티 조회 -> 버전 정보+1 했는지 확인")
    void 비관적락_PESSIMISTIC_FORCE_INCREMENT(){
        CustomerEntity entity = customerService.find(1);
        CustomerEntity entity2 = customerService.findByIdWithPessimisticForceIncrement(1);
        Assertions.assertEquals(entity.getVersion()+1, entity2.getVersion());
    }



}
