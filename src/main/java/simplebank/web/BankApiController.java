package simplebank.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simplebank.model.Account;
import simplebank.model.Customer;
import simplebank.service.AccountService;
import simplebank.service.CustomerService;

@RestController
@RequestMapping("/api")
public class BankApiController {

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public Iterable<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/accounts")
    public Iterable<Account> getAccounts() {
        return accountService.getAccounts();
    }

    @PostMapping("postcustomer")
    public String createCustomer(@RequestBody Customer customer){

        customerService.saveCustomer(customer);

        return "Sucessful";
    }

}
