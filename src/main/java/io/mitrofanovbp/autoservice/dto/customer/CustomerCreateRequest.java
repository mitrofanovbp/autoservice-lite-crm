package io.mitrofanovbp.autoservice.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerCreateRequest(
        @NotBlank String fullName,
        @NotBlank String phone,
        @Email(message = "Invalid email") String email
) {
}
