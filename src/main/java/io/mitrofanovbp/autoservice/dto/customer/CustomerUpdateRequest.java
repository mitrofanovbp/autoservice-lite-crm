package io.mitrofanovbp.autoservice.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerUpdateRequest(
        @NotBlank String fullName,
        @NotBlank String phone,
        @Email(message = "Invalid email") String email
) {
}
