package com.bank.kata.model;

import com.bank.kata.enumeration.OperationType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Operation extends AbstractModel {

    private double amount;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @ManyToOne
    private Account account;

}
