package simplebank.service;

import org.springframework.stereotype.Service;
import simplebank.model.Account;
import simplebank.repository.AccountRepository;


@Service
public class AccountServiceImpl implements AccountService {

    final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Iterable<Account> getAccounts() {
       return accountRepository.findAll();
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
