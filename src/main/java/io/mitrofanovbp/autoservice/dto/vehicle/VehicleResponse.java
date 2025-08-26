package io.mitrofanovbp.autoservice.dto.vehicle;

public record VehicleResponse(
        Long id,
        String vin,
        String make,
        String model,
        Integer productionYear,
        Long ownerId
) {
}
