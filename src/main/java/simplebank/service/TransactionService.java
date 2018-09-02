package simplebank.service;


import simplebank.model.BankTransaction;

public interface TransactionService {

    public Iterable<BankTransaction> getTransactions();

}
