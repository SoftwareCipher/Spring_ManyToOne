package org.boot.spring_manytoone.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    private Long id;

    @Pattern(regexp = "^(0)\\d{9}$", message = "Phone number must start with 0 and contain 9 digits")
    private String phoneNumber;

    private Long personId;

    private String newPhoneNumber;

    public PhoneDTO(Long personId, String phoneNumber) {
        this.personId = personId;
        this.phoneNumber = phoneNumber;
    }

    public PhoneDTO(String phoneNumber, Long personId, String newPhoneNumber) {
        this.phoneNumber = phoneNumber;
        this.personId = personId;
        this.newPhoneNumber = newPhoneNumber;
    }
}