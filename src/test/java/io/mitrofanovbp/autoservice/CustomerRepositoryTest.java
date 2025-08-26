package io.mitrofanovbp.autoservice;

import io.mitrofanovbp.autoservice.entity.Customer;
import io.mitrofanovbp.autoservice.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    @DisplayName("Saving a client and searching for it by id")
    void saveAndFind() {
        Customer c = new Customer();
        c.setFullName("Ivan Ivanov");
        c.setPhone("+77001234567");
        c.setEmail("ivan@example.com");

        Customer saved = repository.save(c);

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).isPresent();
    }
}
