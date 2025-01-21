package org.boot.spring_manytoone.service;

import org.boot.spring_manytoone.dto.PersonDTO;
import org.boot.spring_manytoone.entity.Person;
import org.boot.spring_manytoone.entity.Phone;
import org.boot.spring_manytoone.repo.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public void savePerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());

        if(personDTO.getPhones() != null) {
            List<Phone> phones = personDTO.getPhones().stream()
                    .map(dto ->{
                        Phone phone = new Phone();
                        phone.setPhoneNumber(dto.getPhoneNumber());
                        phone.setPerson(person);
                        return phone;
                    }).toList();
            person.setPhonesList(phones);
        }
        personRepository.save(person);
    }
}
