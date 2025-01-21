package org.boot.spring_manytoone.controller;

import jakarta.validation.Valid;
import org.boot.spring_manytoone.dto.PersonDTO;
import org.boot.spring_manytoone.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

@RestController
@RequestMapping("/v1")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/save")
    public ResponseEntity<PersonDTO> save(@Valid @RequestBody PersonDTO personDTO) {
        Logger.info("save person: " + personDTO);
        personService.savePerson(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
