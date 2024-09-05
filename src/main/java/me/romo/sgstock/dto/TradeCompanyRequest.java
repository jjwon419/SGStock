package me.romo.sgstock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TradeCompanyRequest {
    private Long companyId;
    private int count;
}
