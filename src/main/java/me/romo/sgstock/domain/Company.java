package me.romo.sgstock.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;

    //시가총액 데이터
    @Column(name = "mcT1", nullable = false)
    private long mcT1;
    @Column(name = "mcT2", nullable = false)
    private long mcT2;
    @Column(name = "mcT3", nullable = false)
    private long mcT3;
    @Column(name = "mcT4", nullable = false)
    private long mcT4;
    @Column(name = "mcT5", nullable = false)
    private long mcT5;

    //주가
    @Column(name = "priT1", nullable = false)
    private int priT1;
    @Column(name = "priT2", nullable = false)
    private int priT2;
    @Column(name = "priT3", nullable = false)
    private int priT3;
    @Column(name = "priT4", nullable = false)
    private int priT4;
    @Column(name = "priT5", nullable = false)
    private int priT5;

    //roe
    @Column(name = "roeT1", nullable = false)
    private double roeT1;
    @Column(name = "roeT2", nullable = false)
    private double roeT2;
    @Column(name = "roeT3", nullable = false)
    private double roeT3;
    @Column(name = "roeT4", nullable = false)
    private double roeT4;
    @Column(name = "roeT5", nullable = false)
    private double roeT5;

    //per
    @Column(name = "perT1", nullable = false)
    private double perT1;
    @Column(name = "perT2", nullable = false)
    private double perT2;
    @Column(name = "perT3", nullable = false)
    private double perT3;
    @Column(name = "perT4", nullable = false)
    private double perT4;
    @Column(name = "perT5", nullable = false)
    private double perT5;

    //등락
    @Column(name = "diffRateT2", nullable = false)
    private double diffRateT2;
    @Column(name = "diffRateT3", nullable = false)
    private double diffRateT3;
    @Column(name = "diffRateT4", nullable = false)
    private double diffRateT4;
    @Column(name = "diffRateT5", nullable = false)
    private double diffRateT5;

    protected Company(){
    }
}
