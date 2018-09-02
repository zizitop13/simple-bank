package simplebank.service;

import simplebank.model.Account;
import simplebank.model.BankTransaction;
import simplebank.model.Customer;

public interface CustomerService {

    public Iterable<Customer> getCustomers();

    public Customer saveCustomer(Customer customer);

    public Customer getCustomer(Long id);

    public Iterable<Account> getAccounts();

    public Account getAccount(Long id);

    public Iterable<Account> getCustomerAccounts(Long customerId);

    public Account saveAccount(Long customerId, Account account);

    public BankTransaction transfer(Account sender, Account recipient, Double sum);

    public Iterable<BankTransaction> getTransactions();



}
