package me.romo.sgstock.dto;

import lombok.Getter;
import me.romo.sgstock.domain.Company;
import me.romo.sgstock.domain.CompanyOwned;
import me.romo.sgstock.repository.CompanyRepository;

@Getter
public class CompaniesOwnedListViewResponse {
    private Long companyId;
    private String companyName;
    private int price;
    private int count;
    private double difference;

    public CompaniesOwnedListViewResponse(CompanyOwned companyOwned, CompanyRepository companyRepository){
        Company company = companyRepository.findById(companyOwned.getCompanyOwnedId().getCompanyId()).orElseThrow(
                () -> new IllegalArgumentException("Invalid company ID")
        );
        CompaniesListViewResponse companiesListViewResponse = new CompaniesListViewResponse(company);
        this.companyId = companiesListViewResponse.getId();
        this.companyName = companiesListViewResponse.getName();
        this.price = companiesListViewResponse.getPrice();
        this.count = companyOwned.getCount();
        this.difference = companiesListViewResponse.getDifference();
    }
}
