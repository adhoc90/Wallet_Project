package com.example.walletProject.enums;

import lombok.Getter;

@Getter
public enum OperationType {
    DEPOSIT("Депозит"),
    WITHDRAW("Вывод");

    private final String type;

    OperationType(String type) {
        this.type = type;
    }
}