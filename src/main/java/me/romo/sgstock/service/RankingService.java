package me.romo.sgstock.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.romo.sgstock.domain.CompanyOwned;
import me.romo.sgstock.domain.User;
import me.romo.sgstock.dto.CompaniesListViewResponse;
import me.romo.sgstock.repository.CompanyOwnedRepository;
import me.romo.sgstock.repository.CompanyRepository;
import me.romo.sgstock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RankingService {
    @Getter
    HashMap<String, Integer> list;

    @Getter
    private String updateDate;

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CompanyOwnedRepository companyOwnedRepository;

    //private final HashMap<Integer, String> rankings = new HashMap<Integer, String>();

    @Autowired
    private TaskScheduler taskScheduler;

    @PostConstruct
    public void scheduleTasks(){
        this.updateRanking();
        taskScheduler.schedule(this::updateRanking, new CronTrigger("20 30 9 * * *"));
        taskScheduler.schedule(this::updateRanking, new CronTrigger("20 00 10 * * *"));
        taskScheduler.schedule(this::updateRanking, new CronTrigger("20 30 10 * * *"));
        taskScheduler.schedule(this::updateRanking, new CronTrigger("20 00 11 * * *"));
    }

    public void updateRanking() {
        // Initialize the HashMap to store user rankings
        HashMap<String, Integer> rawList = new HashMap<>();

        // Populate rawList with data
        for (CompanyOwned companyOwned : companyOwnedRepository.findAll()) {
            var usernameOptional = userRepository.findById(companyOwned.getCompanyOwnedId().getOwnerId());
            if (usernameOptional.isEmpty()) {
                continue;
            }
            String username = usernameOptional.get().getPublicId();

            var companyOptional = companyRepository.findById(companyOwned.getCompanyOwnedId().getCompanyId());
            if (companyOptional.isEmpty()) {
                continue;
            }

            CompaniesListViewResponse companiesListViewResponse = new CompaniesListViewResponse(companyOptional.get());
            int price = companiesListViewResponse.getPrice() * companyOwned.getCount();

            // Update the ranking list
            rawList.merge(username, price, Integer::sum);
        }

        // Add money of users to the list
        for (User user : userRepository.findAll()) {
            rawList.merge(user.getPublicId(), user.getMoney(), Integer::sum);
        }

        // Sort the map by value in descending order and keep only the top 10 entries
        list = rawList.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(10)  // Limit to top 10 entries
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Merge function, not needed here
                        LinkedHashMap::new // Maintain insertion order
                ));

        System.out.println("Top 10 Rankings: " + list);

        SimpleDateFormat sdf = new SimpleDateFormat("hh시 mm분 ss초에 갱신된 순위");
        updateDate = sdf.format(new Date());
    }
}
