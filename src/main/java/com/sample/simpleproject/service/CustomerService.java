package com.sample.simpleproject.service;

import com.sample.simpleproject.entity.CustomerEntity;
import com.sample.simpleproject.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    //마일리지 점수 -1 빼기
    @Transactional
    public CustomerEntity decreaseNumber(int id) {
        CustomerEntity entity = customerRepository.findById(id);
        entity.decreaseNumber();
        return customerRepository.save(entity);
    }

    @Transactional
    public CustomerEntity decreaseNumberPessimistic(int id){
        CustomerEntity entity = customerRepository.findByIdWithPessimistic(id);
        entity.decreaseNumber();
        return customerRepository.save(entity);
    }

    @Transactional
    public CustomerEntity changeName(int id, String name){
        CustomerEntity entity = customerRepository.findByIdWithOptimistic(id);
        entity.setName(name);
        return customerRepository.save(entity);
    }

    //select
    public CustomerEntity find(int id){
        return customerRepository.findById(id);
    }


    public CustomerEntity findWithOptimistic(int id){return customerRepository.findByIdWithOptimistic(id);}

    @Transactional
    public CustomerEntity findByIdWithPessimisticForceIncrement(int id){
        return customerRepository.findByIdWithPessimisticForceIncrement(id);
    }

}
