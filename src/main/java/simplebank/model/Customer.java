package simplebank.model;


import javax.persistence.*;
import java.util.Collection;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    public String address;

    @OneToMany(mappedBy = "accountOwner")
    public Collection<Account> accounts;


    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Customer(){}
}
