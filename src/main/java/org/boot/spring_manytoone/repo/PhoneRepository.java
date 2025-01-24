package org.boot.spring_manytoone.repo;

import org.boot.spring_manytoone.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Optional<Phone> findByPhoneNumber(String phoneNumber);

    @Query("select (count(p) > 0) from Phone p where p.phoneNumber = :phoneNumber")
    boolean existsByCheckPhone(String phoneNumber);
}
