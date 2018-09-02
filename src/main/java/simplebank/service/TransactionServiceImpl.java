package simplebank.service;


import org.springframework.beans.factory.annotation.Autowired;
import simplebank.model.BankTransaction;
import simplebank.repository.TransactionRepository;

public class TransactionServiceImpl implements TransactionService {

    final TransactionRepository transactionRepository;


    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Iterable<BankTransaction> getTransactions() {
        return transactionRepository.findAll();
    }
}
