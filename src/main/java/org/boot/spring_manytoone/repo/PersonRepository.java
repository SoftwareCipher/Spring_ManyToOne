package org.boot.spring_manytoone.repo;

import org.boot.spring_manytoone.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT p FROM Person p JOIN FETCH p.phonesList WHERE p.id = :id")
    Person findPersonWithPhones(Long id);
}
