package simplebank.repository;

import org.springframework.data.repository.CrudRepository;
import simplebank.model.BankTransaction;

public interface TransactionRepository extends CrudRepository<BankTransaction, Long> {
}
