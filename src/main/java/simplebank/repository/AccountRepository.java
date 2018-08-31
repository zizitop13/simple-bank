package simplebank.repository;

import org.springframework.data.repository.CrudRepository;
import simplebank.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {}
