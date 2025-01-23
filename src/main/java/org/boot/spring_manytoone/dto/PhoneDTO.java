package org.boot.spring_manytoone.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    @Pattern(
            regexp = "^(\\+380|380|0)\\d{9}$",
            message = "Phone number must start with +380, 380, or 0 and contain 9 digits"
    )
    private String phoneNumber;
}