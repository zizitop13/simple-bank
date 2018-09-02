package simplebank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class BankTransaction {


    @Id
    @GeneratedValue
    private Long transactionId;

    private Date date;

    @JsonIgnoreProperties("transaction")
    @ManyToMany(mappedBy = "transaction")
    private Collection<Account> accounts;

    private Double transferdSum;

    private Double sumBeforeSender;

    private Double sumAfterSender;

    private Double sumBeforeRecipient;

    public BankTransaction(Collection<Account> accounts, Double transferdSum) {

        this.accounts = accounts;

        this.transferdSum = transferdSum;
    }

    public Double getSumBeforeSender() {
        return sumBeforeSender;
    }

    public void setSumBeforeSender(Double sumBeforeSender) {
        this.sumBeforeSender = sumBeforeSender;
    }

    public Double getSumAfterSender() {
        return sumAfterSender;
    }

    public void setSumAfterSender(Double sumAfterSender) {
        this.sumAfterSender = sumAfterSender;
    }

    public Double getSumBeforeRecipient() {
        return sumBeforeRecipient;
    }

    public void setSumBeforeRecipient(Double sumBeforeRecipient) {
        this.sumBeforeRecipient = sumBeforeRecipient;
    }

    public Double getSumAfterRecipient() {
        return sumAfterRecipient;
    }

    public void setSumAfterRecipient(Double sumAfterRecipient) {
        this.sumAfterRecipient = sumAfterRecipient;
    }

    private Double sumAfterRecipient;



    public BankTransaction(){}

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    public Double getTransferdSum() {
        return transferdSum;
    }

    public void setTransferdSum(Double transferdSum) {
        this.transferdSum = transferdSum;
    }





}
