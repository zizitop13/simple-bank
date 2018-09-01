package simplebank.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import simplebank.model.Account;
import simplebank.model.Customer;
import simplebank.service.CustomerService;

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


    @GetMapping("/accounts")
    public Iterable<Account> getAccounts() {
        return customerService.getAccounts();
    }


    @GetMapping("/customerAccounts/{customerId}")
    public Iterable<Account> getAccountsByCustomerId(@PathVariable Long customerId) {
        return customerService.getCustomerAccounts(customerId);
    }

    @PostMapping("postcustomer")
    public String createCustomer(@RequestBody Customer customer){

        customerService.saveCustomer(customer);

        return "Sucessful";
    }


    @PostMapping("postaccount/{customerId}")
    public ResponseEntity<Account> createAccount(@PathVariable Long customerId, @RequestBody Account account){

        Account result = customerService.saveAccount(customerId, account);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setLocation(ServletUriComponentsBuilder

                .fromCurrentRequest().path("postaccount/{customerId}")

                .buildAndExpand(result.id).toUri());


        return new ResponseEntity<>(result, httpHeaders, HttpStatus.CREATED);

    }

}
