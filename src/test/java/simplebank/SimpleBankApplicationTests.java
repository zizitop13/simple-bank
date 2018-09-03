package simplebank;



import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import simplebank.model.Account;
import simplebank.model.BankTransaction;
import simplebank.model.Customer;
import simplebank.repository.AccountRepository;
import simplebank.repository.CustomerRepository;
import simplebank.repository.TransactionRepository;
import static org.mockito.Matchers.refEq;


import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SimpleBankApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    @Before
    public void setUp() throws Exception {
        customerRepository.deleteAll();
        accountRepository.deleteAll();
        transactionRepository.deleteAll();

        Customer customer = customerRepository.save(new Customer("namr", "address"));

        Account account = accountRepository.save(new Account(customer,
                100000d));

        BankTransaction transaction = new BankTransaction(Collections.singletonList(account), 100d);

        transaction.setSenderId(account.getId());

        account.setTransaction(Collections.singletonList(transaction));

        accountRepository.save(account);
    }


    @Test
    @Ignore
    public void indexControllerShouldReturnHtmlPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Simple-bank demo")));
    }

    @Test
    public void apiControllerShouldReturnCustomers() throws Exception {

        mockMvc.perform(get("/api/customers"))
                .andExpect(jsonPath("$.*.name", iterableWithSize(1)));
    }

    @Test
    public void apiControllerShouldReturnAccounts() throws Exception {

        mockMvc.perform(get("/api/accounts"))
                .andExpect(jsonPath("$.*.id", iterableWithSize(1)));
    }

    @Test
    public void apiControllerShouldReturnTransactions() throws Exception {

        mockMvc.perform(get("/api/transactions"))
                .andExpect(jsonPath("$.*.transactionId", iterableWithSize(1)));
    }





}
