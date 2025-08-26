package io.mitrofanovbp.autoservice.controller;

import io.mitrofanovbp.autoservice.dto.vehicle.VehicleCreateRequest;
import io.mitrofanovbp.autoservice.dto.vehicle.VehicleResponse;
import io.mitrofanovbp.autoservice.dto.vehicle.VehicleUpdateRequest;
import io.mitrofanovbp.autoservice.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService service;

    @Autowired
    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse create(@Valid @RequestBody VehicleCreateRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<VehicleResponse> findAll(@RequestParam(required = false) Long ownerId) {
        return service.findAll(ownerId);
    }

    @GetMapping("/{id}")
    public VehicleResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public VehicleResponse update(@PathVariable Long id, @Valid @RequestBody VehicleUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
