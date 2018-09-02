package simplebank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Account {



    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Customer accountOwner;


    private Double sum;


    public Account(){ }

    public Account(Customer accountOwner, Double sum){
        this.accountOwner = accountOwner;
        this.sum = sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(Customer accountOwner) {
        this.accountOwner = accountOwner;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

}
