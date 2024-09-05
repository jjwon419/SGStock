package me.romo.sgstock.dto;

import lombok.Getter;
import me.romo.sgstock.domain.Company;

import java.util.ArrayList;
import java.util.Calendar;

@Getter
public class CompaniesListViewResponse {
    private Long id;
    private String name;
    private long marketCapitalization;
    private int price;
    private double roe;
    private double per;
    private double difference;
    private ArrayList<String> labels = new ArrayList<>();
    private ArrayList<Integer> history = new ArrayList<>();

    public CompaniesListViewResponse(Company company){
        this.id = company.getId();
        this.name = company.getName();

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int min = now.get(Calendar.MINUTE);
        if((hour < 10 && min < 30) || hour < 9){
            this.marketCapitalization = company.getMcT1();
            this.price = company.getPriT1();
            this.roe = company.getRoeT1();
            this.per = company.getPerT1();
            this.difference = 0;

            labels.add("T1");
            history.add(this.price);
        }else if(hour < 10){
            this.marketCapitalization = company.getMcT2();
            this.price = company.getPriT2();
            this.roe = company.getRoeT2();
            this.per = company.getPerT2();
            this.difference = company.getDiffRateT2();

            labels.add("T1");
            labels.add("T2");
            history.add(company.getPriT1());
            history.add(this.price);
        }else if(hour < 11 && min < 30){
            this.marketCapitalization = company.getMcT3();
            this.price = company.getPriT3();
            this.roe = company.getRoeT3();
            this.per = company.getPerT3();
            this.difference = company.getDiffRateT3();

            labels.add("T1");
            labels.add("T2");
            labels.add("T3");
            history.add(company.getPriT1());
            history.add(company.getPriT2());
            history.add(this.price);
        }else if(hour < 11){
            this.marketCapitalization = company.getMcT4();
            this.price = company.getPriT4();
            this.roe = company.getRoeT4();
            this.per = company.getPerT4();
            this.difference = company.getDiffRateT4();

            labels.add("T1");
            labels.add("T2");
            labels.add("T3");
            labels.add("T4");
            history.add(company.getPriT1());
            history.add(company.getPriT2());
            history.add(company.getPriT3());
            history.add(this.price);
        }else{
            this.marketCapitalization = company.getMcT5();
            this.price = company.getPriT5();
            this.roe = company.getRoeT5();
            this.per = company.getPerT5();
            this.difference = company.getDiffRateT5();

            labels.add("T1");
            labels.add("T2");
            labels.add("T3");
            labels.add("T4");
            labels.add("T5");
            history.add(company.getPriT1());
            history.add(company.getPriT2());
            history.add(company.getPriT3());
            history.add(company.getPriT4());
            history.add(this.price);
        }
    }
}
