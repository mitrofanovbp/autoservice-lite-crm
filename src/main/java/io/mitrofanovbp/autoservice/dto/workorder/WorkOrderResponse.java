package io.mitrofanovbp.autoservice.dto.workorder;

import io.mitrofanovbp.autoservice.entity.WorkStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record WorkOrderResponse(
        Long id,
        Long vehicleId,
        WorkStatus status,
        String problemDescription,
        String assignedTo,
        BigDecimal totalCost,
        Instant createdAt,
        Instant updatedAt
) {
}
