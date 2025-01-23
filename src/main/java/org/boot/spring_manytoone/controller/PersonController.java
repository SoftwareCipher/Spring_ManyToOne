package org.boot.spring_manytoone.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.boot.spring_manytoone.dto.*;
import org.boot.spring_manytoone.dto.PhoneDTO;
import org.boot.spring_manytoone.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PersonController {

    // TODO правильно исользовать @RequiredArgsConstructor для DI
    private final PersonService personService;

    @PostMapping("/save")
    public ResponseEntity<PersonDTO> save(@Valid @RequestBody PersonDTO personDTO) {
        Logger.info("save person: " + personDTO);
        personService.savePerson(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{userId}/phone")
    public ResponseEntity<String> savePersonPhone(@PathVariable Long userId,
                                                 @Valid @RequestBody PhoneDTO phoneDTO) {
        personService.savePersonPhone(userId, phoneDTO);
        return ResponseEntity.ok("Phone added successfully");
    }

    @GetMapping("/{id}/without_phone")
    public ResponseEntity<PersonDTO> findByIdWithoutPhone(@PathVariable Long id) {
        Logger.info("findByIdWithoutPhone: " + id);
        return ResponseEntity.ok(personService.getPerson(id));
    }

    @GetMapping("/{id}/with-phone")
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
        Logger.info("find person with phone by id: " + id);
        return ResponseEntity.ok(personService.findByIdWithPhone(id));
    }

    @DeleteMapping("/{id}/phone")
    public void deleteById(@PathVariable Long id) {
        Logger.info("delete person with id: " + id);
        personService.deletePhone(id);
    }
}
