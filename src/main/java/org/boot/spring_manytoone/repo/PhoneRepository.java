package org.boot.spring_manytoone.repo;

import org.boot.spring_manytoone.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
