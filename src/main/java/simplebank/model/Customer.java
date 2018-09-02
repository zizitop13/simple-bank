package simplebank.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Customer {


    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String address;


    @OneToMany(mappedBy = "accountOwner", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("accountOwner")
    private Collection<Account> accounts;


    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Customer(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }


}
