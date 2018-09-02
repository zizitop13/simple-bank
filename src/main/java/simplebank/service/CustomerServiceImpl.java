package simplebank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simplebank.model.Account;
import simplebank.model.Customer;
import simplebank.repository.AccountRepository;
import simplebank.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {


    final CustomerRepository customerRepository;

    final AccountRepository accountRepository;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public Iterable<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Iterable<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Iterable<Account> getCustomerAccounts(Long customerId) {
        return accountRepository.findAccountsByAccountOwner_Id(customerId);
    }

    @Override
    public Account saveAccount(Long customerId, Account account) {
        return customerRepository.findById(customerId).map(customer -> {
            Account createdAccount = accountRepository.save(new Account(customer, account.getSum()));
            return createdAccount;
        }).get();
    }

}
