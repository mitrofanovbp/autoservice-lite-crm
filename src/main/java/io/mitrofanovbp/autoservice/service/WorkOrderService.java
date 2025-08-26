package io.mitrofanovbp.autoservice.service;

import io.mitrofanovbp.autoservice.dto.workorder.WorkOrderCreateRequest;
import io.mitrofanovbp.autoservice.dto.workorder.WorkOrderResponse;
import io.mitrofanovbp.autoservice.dto.workorder.WorkOrderUpdateRequest;
import io.mitrofanovbp.autoservice.entity.Vehicle;
import io.mitrofanovbp.autoservice.entity.WorkOrder;
import io.mitrofanovbp.autoservice.entity.WorkStatus;
import io.mitrofanovbp.autoservice.exception.NotFoundException;
import io.mitrofanovbp.autoservice.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkOrderService {

    private final WorkOrderRepository repository;
    private final VehicleService vehicleService;

    @Autowired
    public WorkOrderService(WorkOrderRepository repository, VehicleService vehicleService) {
        this.repository = repository;
        this.vehicleService = vehicleService;
    }

    public WorkOrderResponse create(WorkOrderCreateRequest req) {
        Vehicle vehicle = vehicleService.getEntity(req.vehicleId());
        WorkOrder wo = new WorkOrder();
        wo.setVehicle(vehicle);
        wo.setProblemDescription(req.problemDescription());
        wo.setAssignedTo(req.assignedTo());
        wo.setTotalCost(req.totalCost());
        wo.setStatus(WorkStatus.OPEN);
        repository.save(wo);
        return toDto(wo);
    }

    public List<WorkOrderResponse> findAll(WorkStatus status) {
        var list = (status == null) ? repository.findAll() : repository.findByStatus(status);
        return list.stream().map(this::toDto).toList();
    }

    public WorkOrderResponse findById(Long id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new NotFoundException("WorkOrder %d not found".formatted(id)));
    }

    public WorkOrderResponse update(Long id, WorkOrderUpdateRequest req) {
        WorkOrder wo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("WorkOrder %d not found".formatted(id)));
        if (req.problemDescription() != null) wo.setProblemDescription(req.problemDescription());
        if (req.assignedTo() != null) wo.setAssignedTo(req.assignedTo());
        if (req.totalCost() != null) wo.setTotalCost(req.totalCost());
        if (req.status() != null) wo.setStatus(req.status());
        repository.save(wo);
        return toDto(wo);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("WorkOrder %d not found".formatted(id));
        repository.deleteById(id);
    }

    private WorkOrderResponse toDto(WorkOrder w) {
        return new WorkOrderResponse(
                w.getId(),
                w.getVehicle().getId(),
                w.getStatus(),
                w.getProblemDescription(),
                w.getAssignedTo(),
                w.getTotalCost(),
                w.getCreatedAt(),
                w.getUpdatedAt()
        );
    }
}
