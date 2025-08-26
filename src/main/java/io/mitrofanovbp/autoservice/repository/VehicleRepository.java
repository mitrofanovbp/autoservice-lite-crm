package io.mitrofanovbp.autoservice.repository;

import io.mitrofanovbp.autoservice.entity.Customer;
import io.mitrofanovbp.autoservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByOwner(Customer owner);

    boolean existsByVin(String vin);
}
