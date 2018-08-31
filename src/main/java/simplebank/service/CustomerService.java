package simplebank.service;

import simplebank.model.Customer;

public interface CustomerService {

    public Iterable<Customer> getCustomers();

    public void saveCustomer(Customer customer);

}
