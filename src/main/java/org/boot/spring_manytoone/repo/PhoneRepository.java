package org.boot.spring_manytoone.repo;

import org.boot.spring_manytoone.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Phone p WHERE p.person.id = :personId")
    boolean existsByPersonId(Long personId);
}
