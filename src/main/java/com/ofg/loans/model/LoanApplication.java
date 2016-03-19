package com.ofg.loans.model;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

/**
 * Loan application entity.
 */
public class LoanApplication extends BaseEntity {
    private Client applicant;
    private LocalDateTime dateTime;
    private Integer amount;
    private InetAddress clientApplicationIP;
    private Collection<Long> fraudRules;

    public LoanApplication() {
    }

    public LoanApplication(Client applicant, LocalDateTime dateTime, Integer amount, Long ... fraudRuleId) {
        this.applicant = applicant;
        this.dateTime = dateTime;
        this.amount = amount;
        this.clientApplicationIP = applicant.getCurrentIP();
        this.fraudRules = Arrays.asList(fraudRuleId);
    }

    public Client getApplicant() {
        return applicant;
    }

    public void setApplicant(Client applicant) {
        this.applicant = applicant;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Collection<Long> getFraudRules() {
        return fraudRules;
    }

    public void setFraudRules(Collection<Long> fraudRules) {
        this.fraudRules = fraudRules;
    }

    public InetAddress getClientApplicationIP() {
        return clientApplicationIP;
    }

    public void setClientApplicationIP(InetAddress clientApplicationIP) {
        this.clientApplicationIP = clientApplicationIP;
    }

    @Override
    public String toString() {
        return "LoanApplication{" +
                "id=" + id +
                "applicant=" + applicant +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", clientApplicationIP=" + clientApplicationIP +
                ", fraudRules=" + fraudRules +
                '}';
    }
}
