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

    private Double sumAfterSender;

    private Double sumAfterRecipient;

    private String discription;

    private Long senderId;

    public BankTransaction(){}

    public BankTransaction(Collection<Account> accounts, Double transferdSum) {

        this.accounts = accounts;

        this.transferdSum = transferdSum;
    }


    public Double getSumAfterSender() {
        return sumAfterSender;
    }

    public void setSumAfterSender(Double sumAfterSender) {
        this.sumAfterSender = sumAfterSender;
    }


    public Double getSumAfterRecipient() {
        return sumAfterRecipient;
    }

    public void setSumAfterRecipient(Double sumAfterRecipient) {
        this.sumAfterRecipient = sumAfterRecipient;
    }


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

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }




}
