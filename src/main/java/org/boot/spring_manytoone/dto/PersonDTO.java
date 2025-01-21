package org.boot.spring_manytoone.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.boot.spring_manytoone.entity.Phone;

import java.util.List;

@Data
@NoArgsConstructor
public class PersonDTO {
    private int id;
    @NotNull(message = "Name must not be null")
    @NotBlank(message = "Name must be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 char")
    private String name;

    @Valid
    private List<Phone> phones;

    public PersonDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
