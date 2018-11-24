package me.migachev.accountmanager.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private String id;
    private BigDecimal amount = BigDecimal.ZERO;
    @CreatedDate
    private LocalDateTime insTime;
    @LastModifiedDate
    private LocalDateTime timeUpdated;

    public Account() {
    }

    public Account(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInsTime(LocalDateTime insTime) {
        this.insTime = insTime;
    }

    public void setTimeUpdated(LocalDateTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public LocalDateTime getInsTime() {
        return insTime;
    }

    public LocalDateTime getTimeUpdated() {
        return timeUpdated;
    }
}
