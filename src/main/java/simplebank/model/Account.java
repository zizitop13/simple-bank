package simplebank.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Account {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public Customer accountOwner;

    public Double sum;

}
