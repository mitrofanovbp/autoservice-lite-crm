package io.mitrofanovbp.autoservice.dto.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VehicleUpdateRequest(
        @NotBlank @Size(min = 5, max = 64) String vin,
        @NotBlank String make,
        @NotBlank String model,
        Integer productionYear
) {
}
