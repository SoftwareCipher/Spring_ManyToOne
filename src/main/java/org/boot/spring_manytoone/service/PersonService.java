package org.boot.spring_manytoone.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Table;
import org.boot.spring_manytoone.dto.PersonDTO;
import org.boot.spring_manytoone.dto.PhoneDTO;
import org.boot.spring_manytoone.entity.Person;
import org.boot.spring_manytoone.entity.Phone;
import org.boot.spring_manytoone.repo.PersonRepository;
import org.boot.spring_manytoone.repo.PhoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PhoneRepository phoneRepository;

    public PersonService(PersonRepository personRepository,
                         PhoneRepository phoneRepository) {
        this.personRepository = personRepository;
        this.phoneRepository = phoneRepository;
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
                        phone.setFlag(dto.isFlag());
                        return phone;
                    }).toList();

            if (phones.stream().filter(Phone::isFlag).count() > 1) {
                throw new IllegalArgumentException("Only one phone number can be marked as primary");
            }

            boolean hasPrimary = phones.stream().anyMatch(Phone::isFlag);
            if (!hasPrimary && !phones.isEmpty()) {
                phones.get(0).setFlag(true);
            }

            person.setPhonesList(phones);
        }

        personRepository.save(person);
    }

    @Transactional
    public void savePersonPhone(Long id, PhoneDTO phoneDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        boolean hasPhone = person.getPhonesList() != null
                && !person.getPhonesList().isEmpty();

        Phone phone = new Phone();
        phone.setPerson(person);
        phone.setFlag(!hasPhone);
        phone.setPhoneNumber(phoneDTO.getPhoneNumber());

        if(person.getPhonesList() == null){
            person.setPhonesList(new ArrayList<>());
        }

        person.getPhonesList().add(phone);
        personRepository.save(person);
    }

    public PersonDTO getPerson(Long id) {
        return personRepository.findById(id)
                .map(person -> new PersonDTO(
                        person.getId(),
                        person.getName()
                )).orElseThrow(() -> new EntityNotFoundException("Person not found"));
    }

    public PersonDTO findByIdWithPhone(Long id) {
        Person person = personRepository.findPersonWithPhones(id);
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getPhonesList()
        );
    }

    @Transactional
    public void deletePhone(Long id) {
        Phone phone = phoneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Phone not found"));

        if (phone.isFlag()) {
            throw new IllegalStateException("Cannot delete the primary phone number");
        }

        phoneRepository.delete(phone);
    }
}
