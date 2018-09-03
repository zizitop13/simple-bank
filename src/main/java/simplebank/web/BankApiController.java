package simplebank.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import simplebank.model.Account;
import simplebank.model.BankTransaction;
import simplebank.model.Customer;
import simplebank.service.CustomerService;
import simplebank.service.CustomerServiceImpl;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class BankApiController {


    final CustomerService customerService;


    @Autowired
    public BankApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public Iterable<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId);
    }


    @GetMapping("/transactions")
    public Iterable<BankTransaction> getTransactions() {
        return customerService.getTransactions();
    }

    @GetMapping("/accounts")
    public Iterable<Account> getAccounts() {
        return customerService.getAccounts();
    }


    @GetMapping("/customerAccounts/{customerId}")
    public Iterable<Account> getAccountsByCustomerId(@PathVariable Long customerId) {
        return customerService.getCustomerAccounts(customerId);
    }

    @PostMapping("/postcustomer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){

        Customer result = customerService.saveCustomer(customer);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setLocation(ServletUriComponentsBuilder

                .fromCurrentRequest().path("postcustomer")

                .buildAndExpand(result.getId()).toUri());


        return new ResponseEntity<Customer>(result, httpHeaders, HttpStatus.CREATED);
    }


    @PostMapping("postaccount/{customerId}")
    public ResponseEntity<Account> createAccount(@PathVariable Long customerId, @RequestBody Account account){

        Account result = customerService.saveAccount(customerId, account);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setLocation(ServletUriComponentsBuilder

                .fromCurrentRequest().path("postaccount/{customerId}")

                .buildAndExpand(result.getId()).toUri());


        return new ResponseEntity<>(result, httpHeaders, HttpStatus.CREATED);

    }

    @PostMapping("transfer/{senderId}/{recipientId}/{sum}")
    public ResponseEntity<BankTransaction> transfer(@PathVariable Long senderId, @PathVariable Long recipientId, @PathVariable Double sum){


        Account sender = validateAccount(senderId);

        Account recipient = validateAccount(recipientId);

        if(sender.getSum() < sum)
            throw new AccountTransferException(sum);


        BankTransaction result = customerService.transfer(sender, recipient, sum);



        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setLocation(ServletUriComponentsBuilder

                .fromCurrentRequest().path("transfer/{senderId}/{recipientId}/{sum}")

                .buildAndExpand(senderId, recipientId, sum).toUri());


        return new ResponseEntity<BankTransaction>(result, httpHeaders, HttpStatus.CREATED);
    }


    private Account validateAccount(Long id) {

        Account account = customerService.getAccount(id);

        if(account==null)
            throw new AccountNotFoundException(id);

        return account;

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class AccountNotFoundException extends RuntimeException {

        public AccountNotFoundException(Long id) {
            super("could not find account '" + id + "'.");
        }

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class AccountTransferException extends RuntimeException {

        public AccountTransferException(Double sum) {
            super("The sender does not have enough money on the account: '" + sum + "'.");
        }

    }

}
