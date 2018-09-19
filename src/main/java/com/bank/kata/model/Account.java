package com.bank.kata.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Account extends AbstractModel {

    private double amount;

    private double overDraft;

    private Long balance;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Operation> operationList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RIB rib;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
}
