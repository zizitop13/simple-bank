package simplebank.repository;

import org.springframework.data.repository.CrudRepository;
import simplebank.model.Account;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findAccountsByAccountOwner_Id(Long ownerId);

}
