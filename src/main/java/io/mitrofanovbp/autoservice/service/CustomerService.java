package io.mitrofanovbp.autoservice.service;

import io.mitrofanovbp.autoservice.dto.customer.CustomerCreateRequest;
import io.mitrofanovbp.autoservice.dto.customer.CustomerResponse;
import io.mitrofanovbp.autoservice.dto.customer.CustomerUpdateRequest;
import io.mitrofanovbp.autoservice.entity.Customer;
import io.mitrofanovbp.autoservice.exception.NotFoundException;
import io.mitrofanovbp.autoservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerResponse create(CustomerCreateRequest req) {
        Customer c = new Customer();
        c.setFullName(req.fullName());
        c.setPhone(req.phone());
        c.setEmail(req.email());
        repository.save(c);
        return toDto(c);
    }

    public List<CustomerResponse> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public CustomerResponse findById(Long id) {
        return repository.findById(id).map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Customer %d not found".formatted(id)));
    }

    public CustomerResponse update(Long id, CustomerUpdateRequest req) {
        Customer c = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer %d not found".formatted(id)));
        c.setFullName(req.fullName());
        c.setPhone(req.phone());
        c.setEmail(req.email());
        repository.save(c);
        return toDto(c);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Customer %d not found".formatted(id));
        }
        repository.deleteById(id);
    }

    public Customer getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer %d not found".formatted(id)));
    }

    private CustomerResponse toDto(Customer c) {
        return new CustomerResponse(c.getId(), c.getFullName(), c.getPhone(), c.getEmail());
    }
}
