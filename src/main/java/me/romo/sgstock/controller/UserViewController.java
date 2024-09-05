package me.romo.sgstock.controller;

import lombok.RequiredArgsConstructor;
import me.romo.sgstock.domain.Company;
import me.romo.sgstock.domain.CompanyOwned;
import me.romo.sgstock.domain.User;
import me.romo.sgstock.dto.CompaniesListViewResponse;
import me.romo.sgstock.dto.CompaniesOwnedListViewResponse;
import me.romo.sgstock.dto.RankingResponse;
import me.romo.sgstock.service.CompanyService;
import me.romo.sgstock.service.RankingService;
import me.romo.sgstock.service.TimeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final CompanyService companyService;
    private final RankingService rankingService;
    private final TimeService timeService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("index")
    public String index(Model model, @AuthenticationPrincipal User user){
        if(user == null){
            return "index";
        }

        model.addAttribute("status", timeService.getStatus());

        int sumMoney = 0;
        //총 금액
        for (CompanyOwned companyOwned : companyService.getAllCompaniesOwnedByUserId(user.getId())) {
            var companyOptional = companyService.getCompany(companyOwned.getCompanyOwnedId().getCompanyId());
            if(companyOptional.isEmpty()){
                continue;
            }
            Company company = companyOptional.get();
            CompaniesListViewResponse companiesListViewResponse = new CompaniesListViewResponse(company);
            sumMoney += companiesListViewResponse.getPrice() * companyOwned.getCount();
        }
        model.addAttribute("sumMoney", user.getMoney() + sumMoney);

        //남은 예수금
        model.addAttribute("money", user.getMoney());

        model.addAttribute("update_date_time", rankingService.getUpdateDate());

        //순위
        ArrayList<RankingResponse> rankingResponses = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);
        rankingService.getList().forEach((name, money) -> {
            int currentCount = count.getAndIncrement();
            rankingResponses.add(new RankingResponse(currentCount, maskName(name), money));
        });
        model.addAttribute("ranking", rankingResponses);

        //현재 보유중인 주식
        List<CompaniesOwnedListViewResponse> companiesOwned = companyService.getAllCompaniesOwnedByUserId(user.getId()).stream()
                .map(companyOwned -> new CompaniesOwnedListViewResponse(companyOwned, companyService.getCompanyRepository()))
                .toList();

        //
        List<CompaniesListViewResponse> companies = companyService.getAllCompanies().stream()
                .map(CompaniesListViewResponse::new)
                .toList();

        model.addAttribute("companiesOwned", companiesOwned);
        model.addAttribute("companies", companies);
        return "index";
    }

    @GetMapping("admin/ranking")
    public String ranking(Model model){
        ArrayList<RankingResponse> rankingResponses = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);
        rankingService.getList().forEach((name, money) -> {
            int currentCount = count.getAndIncrement();
            rankingResponses.add(new RankingResponse(currentCount, name, money));
        });
        model.addAttribute("ranking", rankingResponses);
        return "ranking";
    }

    public static String maskName(String name) {
        // 숫자와 한글 이름 패턴 (공백 없이)
        Pattern pattern = Pattern.compile("(\\d+)([가-힣]+)");
        Matcher matcher = pattern.matcher(name);

        if (matcher.find()) {
            String id = matcher.group(1);
            String realName = matcher.group(2);

            // 이름의 중간 부분을 별로 가리기 (예: 조재원 -> 조**원)
            int length = realName.length();
            if (length > 2) {
                String maskedRealName = realName.charAt(0) + "*".repeat(length - 2) + realName.charAt(length - 1);
                return id + maskedRealName;
            } else {
                // 이름이 너무 짧아서 가릴 수 없는 경우
                return id + realName;
            }
        } else {
            return name; // 형식이 맞지 않으면 원래 이름을 반환
        }
    }
}
