package io.mitrofanovbp.autoservice.controller;

import io.mitrofanovbp.autoservice.dto.workorder.WorkOrderCreateRequest;
import io.mitrofanovbp.autoservice.dto.workorder.WorkOrderResponse;
import io.mitrofanovbp.autoservice.dto.workorder.WorkOrderUpdateRequest;
import io.mitrofanovbp.autoservice.entity.WorkStatus;
import io.mitrofanovbp.autoservice.service.WorkOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {

    private final WorkOrderService service;

    @Autowired
    public WorkOrderController(WorkOrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkOrderResponse create(@Valid @RequestBody WorkOrderCreateRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<WorkOrderResponse> findAll(@RequestParam(required = false) WorkStatus status) {
        return service.findAll(status);
    }

    @GetMapping("/{id}")
    public WorkOrderResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public WorkOrderResponse update(@PathVariable Long id, @RequestBody WorkOrderUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
