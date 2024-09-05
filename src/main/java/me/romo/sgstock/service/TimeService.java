package me.romo.sgstock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@RequiredArgsConstructor
@Service
public class TimeService {
    public int getStatus(){
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        if(hour < 9){
            return 1;
        }
        if(hour > 10){
            return 2;
        }

        return 0;
    }
}
