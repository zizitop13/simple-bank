package simplebank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplebank.model.Account;
import simplebank.model.BankTransaction;
import simplebank.model.Customer;
import simplebank.repository.AccountRepository;
import simplebank.repository.CustomerRepository;
import simplebank.repository.TransactionRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;


@Service
public class CustomerServiceImpl implements CustomerService {


    final CustomerRepository customerRepository;

    final AccountRepository accountRepository;

    final TransactionRepository transactionRepository;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public Iterable<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(Long id) {
        return accountRepository.findById(id).get();
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

    @Transactional
    @Override
    public BankTransaction transfer(Account sender, Account recipient, Double sum) {


        Double sumBeforeSender =  sender.getSum();

        Double sumAfterSender =  sumBeforeSender - sum;

        Double sumBeforeRecipient =  recipient.getSum();

        Double sumAfterRecipient =  sumBeforeRecipient + sum;



        sender.setSum(sumAfterSender);

        recipient.setSum(sumAfterRecipient);


        BankTransaction bankTransaction = new BankTransaction(Arrays.asList(sender, recipient), sum);

        bankTransaction.setSumAfterRecipient(sumAfterRecipient);

        bankTransaction.setSumBeforeRecipient(sumBeforeRecipient);

        bankTransaction.setDate(new Date(System.currentTimeMillis()));

        bankTransaction.setSumAfterSender(sumAfterSender);

        bankTransaction.setSumBeforeSender(sumBeforeSender);


        sender.getTransaction().add(bankTransaction);

        recipient.getTransaction().add(bankTransaction);


        return transactionRepository.save(bankTransaction);

    }

    @Override
    public Iterable<BankTransaction> getTransactions() {
        return transactionRepository.findAll();
    }


}
