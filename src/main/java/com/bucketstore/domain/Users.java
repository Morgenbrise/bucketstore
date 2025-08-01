package com.bucketstore.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;
    private String zipCode;

    @OneToMany(mappedBy = "users")
    private List<Orders> orders = new ArrayList<>();
}
