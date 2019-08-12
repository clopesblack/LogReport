package com.dn.model;

import lombok.Getter;

@Getter
public class Summary {

    private final Integer count;
    private final Integer duplicates;
    private final Integer unnecessary;

    public Summary(final Integer count, final Integer duplicates, final Integer unnecessary) {
        this.count = count;
        this.duplicates = duplicates;
        this.unnecessary = unnecessary;
    }
}