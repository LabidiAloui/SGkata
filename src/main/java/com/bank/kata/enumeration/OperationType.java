package com.bank.kata.enumeration;

import java.util.Objects;

public enum OperationType {

    DEPOSIT("virement"),
    WITHDRAWAL("retrait");

    private String labelOperationType;

    OperationType(String labelOperationType) {
        this.labelOperationType = labelOperationType;
    }

    public static OperationType convertStringToOperationType(String stringOperationType){
        for(OperationType operationType : OperationType.values()){
            if(operationType.toString().equals(stringOperationType)
                    || (Objects.nonNull(operationType.labelOperationType)
                    && operationType.labelOperationType.equals(stringOperationType)) ){
                return operationType;
            }
        }
        throw new RuntimeException("Error converting enum :" + stringOperationType);
    }
}
