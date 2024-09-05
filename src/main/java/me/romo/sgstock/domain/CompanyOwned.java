package me.romo.sgstock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class CompanyOwned {

    @EmbeddedId
    private CompanyOwnedId companyOwnedId;

    @Column(name = "count")
    private int count;

    @Builder
    public CompanyOwned(CompanyOwnedId companyOwnedId, int count){
        this.companyOwnedId = companyOwnedId;
        this.count = count;
    }

    protected CompanyOwned(){

    }

    /**
     * Adds the specified amount to the current count.
     *
     * @param amount the amount to be added
     * @throws IllegalArgumentException if the amount is negative
     */
    public void addCount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to add cannot be negative");
        }
        this.count += amount;
    }

    /**
     * Subtracts the specified amount from the current count.
     *
     * @param amount the amount to be subtracted
     * @throws IllegalArgumentException if the amount is negative or exceeds the current count
     */
    public void reduceCount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to subtract cannot be negative");
        }
        if (amount > this.count) {
            throw new IllegalArgumentException("Cannot subtract more than the current count");
        }
        this.count -= amount;
    }
}
