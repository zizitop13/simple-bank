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

    @OneToMany(mappedBy = "accountOwner", cascade = CascadeType.ALL)
    public Collection<Account> accounts;


}
