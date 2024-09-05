package me.romo.sgstock.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.romo.sgstock.domain.Company;
import me.romo.sgstock.domain.CompanyOwned;
import me.romo.sgstock.domain.CompanyOwnedId;
import me.romo.sgstock.domain.User;
import me.romo.sgstock.repository.CompanyOwnedRepository;
import me.romo.sgstock.repository.CompanyRepository;
import me.romo.sgstock.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CompanyService {

    @Getter
    private final CompanyRepository companyRepository;
    private final CompanyOwnedRepository companyOwnedRepository;
    private final UserRepository userRepository;

    public Optional<Company> getCompany(Long id){
        return companyRepository.findById(id);
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public List<CompanyOwned> getAllCompaniesOwnedByUserId(Long userId){
        return companyOwnedRepository.findByCompanyOwnedIdOwnerId(userId);
    }

    public Optional<CompanyOwned> getCompanyOwned(CompanyOwnedId companyOwnedId){
        return companyOwnedRepository.findById(companyOwnedId);
    }


    @Transactional
    public void buyCompany(User user, Company company, int count, int price){
        user.reduceMoney(price);
        userRepository.save(user);
        CompanyOwnedId id = new CompanyOwnedId(user.getId(), company.getId());
        var companyOwnedOptional = companyOwnedRepository.findById(id);
        if(companyOwnedOptional.isEmpty()){ //소유 데이터가 없을 때
            companyOwnedRepository.save(CompanyOwned.builder().companyOwnedId(id).count(count).build());
        }else{ //소유 데이터가 있을 때
            companyOwnedOptional.get().addCount(count);
        }
    }

    @Transactional
    public void sellCompany(User user, CompanyOwned companyOwned, int count, int price){
        if(companyOwned.getCount() == count){
            companyOwnedRepository.delete(companyOwned);
        }else{
            companyOwned.reduceCount(count);
        }
        user.addMoney(price);
        userRepository.save(user);
    }
}
