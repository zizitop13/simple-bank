package simplebank.repository;

import org.springframework.data.repository.CrudRepository;
import simplebank.model.Account;
import simplebank.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findCustomerByAccountsContains(Account account);



}
