package io.mitrofanovbp.autoservice.repository;

import io.mitrofanovbp.autoservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
