package me.romo.sgstock.dto;

import lombok.Getter;

@Getter
public class RankingResponse {
    private final int rank;
    private final String name;
    private final int money;

    public RankingResponse(int rank, String name, Integer money) {
        this.rank = rank;
        this.name = name;
        this.money = money;
    }
}
