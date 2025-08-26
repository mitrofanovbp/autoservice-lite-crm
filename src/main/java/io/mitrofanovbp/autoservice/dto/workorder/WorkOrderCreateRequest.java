package io.mitrofanovbp.autoservice.dto.workorder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WorkOrderCreateRequest(
        @NotNull Long vehicleId,
        @NotBlank String problemDescription,
        String assignedTo,
        BigDecimal totalCost
) {
}
