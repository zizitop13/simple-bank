package simplebank.service;

import org.springframework.stereotype.Service;
import simplebank.model.Customer;
import simplebank.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {


    final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Iterable<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
