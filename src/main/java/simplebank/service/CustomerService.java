package simplebank.service;

import simplebank.model.Account;
import simplebank.model.Customer;

public interface CustomerService {

    public Iterable<Customer> getCustomers();

    public Customer saveCustomer(Customer customer);

    public Iterable<Account> getAccounts();

    public Iterable<Account> getCustomerAccounts(Long customerId);

    public Account saveAccount(Long customerId, Account account);


}
