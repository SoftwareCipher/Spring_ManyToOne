package org.boot.spring_manytoone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean flag;

    @Pattern(
            regexp = "^(\\+380|380|0)\\d{9}$",
            message = "Phone number must start with +380, 380, or 0 and contain 9 digits"
    )
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Person person;
}
