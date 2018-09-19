package com.bank.kata.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Person extends AbstractModel {

    private String firstName;

    private String lastName;

    private String address;

}
