package com.sample.simpleproject.repository;

import com.sample.simpleproject.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    public List<CustomerEntity> findAll();

    //Lock NONE 옵션
    public CustomerEntity findById(int id);

    //OPTIMISTIC 옵션
    @Lock(LockModeType.OPTIMISTIC)
    @Query("select c from CustomerEntity c where c.id = :id")
    public CustomerEntity findByIdWithOptimistic(int id);

    //Lock PESSIMISTIC_WRITE 옵션
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from CustomerEntity c where c.id = :id")
    public CustomerEntity findByIdWithPessimistic(int id);


    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @Query("select c from CustomerEntity c where c.id = :id")
    public CustomerEntity findByIdWithPessimisticForceIncrement(int id);


}
