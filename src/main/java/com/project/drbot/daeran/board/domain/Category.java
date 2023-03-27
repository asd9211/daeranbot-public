package com.project.drbot.daeran.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    ELECTRICS("1"),
    CLOTHES("2"),
    FOODS("3"),
    COUPONS("4"),
    ETC("0")
    ;

    private String code;

    Category(String code) {
        this.code = code;
    }
}
