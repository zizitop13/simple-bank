package simplebank.service;

import simplebank.model.Account;

public interface AccountService {

    public Iterable<Account> getAccounts();

    public void saveAccount(Account account);


}
