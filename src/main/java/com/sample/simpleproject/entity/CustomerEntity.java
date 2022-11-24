package com.sample.simpleproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;                 //PK
    private String name;            //이름
    private int mileage;         //마일리지

    @Version
    private Integer version;            //버전

    public void decreaseNumber(){
        mileage--;
    }
}
