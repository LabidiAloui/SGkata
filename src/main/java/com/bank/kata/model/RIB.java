package com.bank.kata.model;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Data
public class RIB extends AbstractModel {

    @NotNull
    private Long code;

    @NotNull
    private String accountNumber;

    @NotNull
    private String bankKey;

    @NotNull
    private String bankAccountName;

    @NotNull
    private String iban;

    @NotNull
    private String bic;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

}
