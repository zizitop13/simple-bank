package simplebank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import simplebank.model.Account;
import simplebank.model.Customer;
import simplebank.repository.AccountRepository;
import simplebank.repository.CustomerRepository;

import java.util.Arrays;

@SpringBootApplication
public class SimpleBankApplication {

    @Bean
    CommandLineRunner init(CustomerRepository customerRepository,
                           AccountRepository accountRepository) {



        return (names) -> Arrays.asList(
                "Иванов Сергей Юрьевич, Петров Александр Сергеевич".split(","))
                .forEach(
                        name -> {
                            Customer customer = customerRepository.save(new Customer(name, "г. Мытищи, улица Первомайская 19"));

                            Account account = accountRepository.save(new Account(customer,
                                    100000d));

                            accountRepository.save(account);

                        });
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleBankApplication.class, args);
    }
}
