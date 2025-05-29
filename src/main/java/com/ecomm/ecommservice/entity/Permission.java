package com.ecomm.ecommservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Table(name = "permissions")
@Data
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, unique = true, length = 255)
    private String name;
}
