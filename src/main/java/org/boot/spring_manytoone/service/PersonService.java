package org.boot.spring_manytoone.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.boot.spring_manytoone.dto.PersonDTO;
import org.boot.spring_manytoone.dto.PhoneDTO;
import org.boot.spring_manytoone.entity.Person;
import org.boot.spring_manytoone.entity.Phone;
import org.boot.spring_manytoone.repo.PersonRepository;
import org.boot.spring_manytoone.repo.PhoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tinylog.Logger;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PhoneRepository phoneRepository;

    @Transactional
    public void savePerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());

        if (!phoneRepository.existsByCheckPhone(personDTO.getMainPhone())) {
            person.setMainPhone(personDTO.getMainPhone());
        }

        if (personDTO.getPhones() != null) {
            List<Phone> phones = personDTO.getPhones().stream()
                    .map(dto -> {
                        Phone phone = new Phone();
                        phone.setPhoneNumber(dto.getPhoneNumber());
                        phone.setPerson(person);
                        return phone;
                    }).toList();
            person.setPhonesList(phones);
        }
        personRepository.save(person);
    }

    private Phone checkPhoneAndPerson(PhoneDTO phoneDTO) {
        Phone phone = phoneRepository.findByPhoneNumber(phoneDTO.getPhoneNumber())
                .orElseThrow(() -> new EntityNotFoundException("Phone with number "
                        + phoneDTO.getPhoneNumber() + " not found"));

        Logger.info("Found phone: " + phone.getPhoneNumber() + ", Person ID: " + phone.getPerson().getId());

        if (!phone.getPerson().getId().equals(phoneDTO.getPersonId())) {
            throw new SecurityException("Phone does not belong to the specified person. Found Person ID: "
                    + phone.getPerson().getId() + ", Provided Person ID: " + phoneDTO.getPersonId());
        }

        return phone;
    }

    @Transactional
    public void savePersonPhone(PhoneDTO phoneDTO) {
        Phone phone = checkPhoneAndPerson(phoneDTO);

        phone.setPhoneNumber(phoneDTO.getNewPhoneNumber());
        phoneRepository.save(phone);

        Logger.info("Phone with ID " + phoneDTO.getId() +
                " updated to new number: " + phoneDTO.getNewPhoneNumber());
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
                person.getMainPhone(),
                person.getPhonesList()
        );
    }

    @Transactional
    public void deletePhone(PhoneDTO phoneDTO) {
        Phone phone = checkPhoneAndPerson(phoneDTO);

        Logger.info("Deleting phone: " + phone.getPhoneNumber());
        phoneRepository.delete(phone);
    }
}
