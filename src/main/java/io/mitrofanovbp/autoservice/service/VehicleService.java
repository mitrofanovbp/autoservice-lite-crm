package io.mitrofanovbp.autoservice.service;

import io.mitrofanovbp.autoservice.dto.vehicle.VehicleCreateRequest;
import io.mitrofanovbp.autoservice.dto.vehicle.VehicleResponse;
import io.mitrofanovbp.autoservice.dto.vehicle.VehicleUpdateRequest;
import io.mitrofanovbp.autoservice.entity.Customer;
import io.mitrofanovbp.autoservice.entity.Vehicle;
import io.mitrofanovbp.autoservice.exception.NotFoundException;
import io.mitrofanovbp.autoservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository repository;
    private final CustomerService customerService;

    @Autowired
    public VehicleService(VehicleRepository repository, CustomerService customerService) {
        this.repository = repository;
        this.customerService = customerService;
    }

    public VehicleResponse create(VehicleCreateRequest req) {
        Customer owner = customerService.getEntity(req.ownerId());
        Vehicle v = new Vehicle();
        v.setVin(req.vin());
        v.setMake(req.make());
        v.setModel(req.model());
        v.setProductionYear(req.productionYear());
        v.setOwner(owner);
        repository.save(v);
        return toDto(v);
    }

    public List<VehicleResponse> findAll(Long ownerId) {
        if (ownerId != null) {
            Customer owner = customerService.getEntity(ownerId);
            return repository.findByOwner(owner).stream().map(this::toDto).toList();
        }
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public VehicleResponse findById(Long id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Vehicle %d not found".formatted(id)));
    }

    public VehicleResponse update(Long id, VehicleUpdateRequest req) {
        Vehicle v = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle %d not found".formatted(id)));
        v.setVin(req.vin());
        v.setMake(req.make());
        v.setModel(req.model());
        v.setProductionYear(req.productionYear());
        repository.save(v);
        return toDto(v);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Vehicle %d not found".formatted(id));
        repository.deleteById(id);
    }

    public Vehicle getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle %d not found".formatted(id)));
    }

    private VehicleResponse toDto(Vehicle v) {
        return new VehicleResponse(v.getId(), v.getVin(), v.getMake(), v.getModel(),
                v.getProductionYear(), v.getOwner().getId());
    }
}
