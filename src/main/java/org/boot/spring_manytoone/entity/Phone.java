package org.boot.spring_manytoone.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;
}
