package io.mitrofanovbp.autoservice.dto.customer;

public record CustomerResponse(
        Long id,
        String fullName,
        String phone,
        String email
) {
}
