package simplebank;



import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import simplebank.model.Account;
import simplebank.model.BankTransaction;
import simplebank.model.Customer;
import simplebank.repository.AccountRepository;
import simplebank.repository.CustomerRepository;
import simplebank.repository.TransactionRepository;
import static org.mockito.Matchers.refEq;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SimpleBankApplicationTests {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }



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



    @Test
    public void apiControllerPostCustomerShouldReturnCustomer() throws Exception {
        String customerJson = json(new Customer(
                "name", "address"));

        mockMvc.perform(post("/api/postcustomer")
                .contentType(contentType)
                .content(customerJson))
                .andExpect(status().isCreated());
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }





}
