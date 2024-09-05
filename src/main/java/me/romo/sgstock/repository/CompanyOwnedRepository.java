package me.romo.sgstock.repository;

import me.romo.sgstock.domain.CompanyOwned;
import me.romo.sgstock.domain.CompanyOwnedId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyOwnedRepository extends JpaRepository<CompanyOwned, CompanyOwnedId> {
    List<CompanyOwned> findByCompanyOwnedIdOwnerId(Long ownerId);
}
