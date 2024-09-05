package me.romo.sgstock.controller;

import lombok.RequiredArgsConstructor;
import me.romo.sgstock.domain.Company;
import me.romo.sgstock.domain.CompanyOwned;
import me.romo.sgstock.domain.CompanyOwnedId;
import me.romo.sgstock.domain.User;
import me.romo.sgstock.dto.TradeCompanyRequest;
import me.romo.sgstock.dto.CompaniesListViewResponse;
import me.romo.sgstock.dto.RequestResponse;
import me.romo.sgstock.service.CompanyService;
import me.romo.sgstock.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CompanyApiController {

    private final CompanyService companyService;
    private final TimeService timeService;

    @PostMapping("/api/buy")
    public ResponseEntity<RequestResponse> buyCompany(@RequestBody TradeCompanyRequest request, @AuthenticationPrincipal User user){
        if(timeService.getStatus() != 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RequestResponse(1, "현재 시장이 열려있지 않습니다. 나중에 다시 시도해주세요!"));
        }
        if(request.getCount() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RequestResponse(1, "수량을 1이상으로 입력해주세요."));
        }

        var companyOptional = companyService.getCompany(request.getCompanyId());

        if (companyOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RequestResponse(1, "회사를 찾을 수 없습니다."));
        }

        Company company = companyOptional.get();
        CompaniesListViewResponse companiesListViewResponse = new CompaniesListViewResponse(company);

        //예수금이 충분한지 확인
        int price = companiesListViewResponse.getPrice() * request.getCount();
        if(user.getMoney() < price){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RequestResponse(1, "예수금이 부족합니다."));
        }

        companyService.buyCompany(user, company, request.getCount(), price);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponse(0, "성공적으로 매수 하였습니다!"));
    }

    @PostMapping("/api/sell")
    public ResponseEntity<RequestResponse> sellCompany(@RequestBody TradeCompanyRequest request, @AuthenticationPrincipal User user){
        if(timeService.getStatus() != 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RequestResponse(1, "현재 시장이 열려있지 않습니다. 나중에 다시 시도해주세요!"));
        }
        if(request.getCount() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RequestResponse(1, "수량을 1이상으로 입력해주세요."));
        }

        var companyOptional = companyService.getCompany(request.getCompanyId());

        if (companyOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RequestResponse(1, "회사를 찾을 수 없습니다."));
        }

        Company company = companyOptional.get();
        CompaniesListViewResponse companiesListViewResponse = new CompaniesListViewResponse(company);

        //갯수가 충분한지
        var companyOwnedOptional = companyService.getCompanyOwned(new CompanyOwnedId(user.getId(), company.getId()));
        if(companyOwnedOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RequestResponse(1, "판매할 주식이 없습니다."));
        }

        CompanyOwned companyOwned = companyOwnedOptional.get();
        if(companyOwned.getCount() < request.getCount()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RequestResponse(1, "판매할 주식의 수량이 부족합니다."));
        }

        //들어올 가격
        int price = companiesListViewResponse.getPrice() * request.getCount();

        companyService.sellCompany(user, companyOwned, request.getCount(), price);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponse(0, "성공적으로 매도 하였습니다!"));
    }
}
