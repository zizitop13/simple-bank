package simplebank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Account {


    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    @JsonIgnoreProperties("accounts")
    private Customer accountOwner;

    @JsonIgnoreProperties("accounts")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "TransactionAccount",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_Id")
    )
    private Collection<BankTransaction> transaction;


    private Double sum;


    public Account() {
    }

    public Account(Customer accountOwner, Double sum) {
        this.accountOwner = accountOwner;
        this.sum = sum;
    }

    public Collection<BankTransaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(Collection<BankTransaction> transaction) {
        this.transaction = transaction;
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
