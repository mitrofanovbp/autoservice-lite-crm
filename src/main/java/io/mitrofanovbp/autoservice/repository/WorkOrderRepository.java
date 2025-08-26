package io.mitrofanovbp.autoservice.repository;

import io.mitrofanovbp.autoservice.entity.WorkOrder;
import io.mitrofanovbp.autoservice.entity.WorkStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findByStatus(WorkStatus status);
}
