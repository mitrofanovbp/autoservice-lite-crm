package io.mitrofanovbp.autoservice.dto.workorder;

import io.mitrofanovbp.autoservice.entity.WorkStatus;

import java.math.BigDecimal;

public record WorkOrderUpdateRequest(
        String problemDescription,
        String assignedTo,
        BigDecimal totalCost,
        WorkStatus status
) {
}
