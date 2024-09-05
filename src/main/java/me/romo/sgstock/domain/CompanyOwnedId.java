package me.romo.sgstock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class CompanyOwnedId implements Serializable {
    @Column(name = "ownerId", updatable = false)
    private Long ownerId;

    @Column(name = "companyId", updatable = false)
    private Long companyId;
}
