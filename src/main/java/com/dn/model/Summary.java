package com.dn.model;

public class Summary {

    private final Integer count;
    private final Integer duplicates;
    private final Integer unnecessary;

    public Summary(final Integer count, final Integer duplicates, final Integer unnecessary) {
        this.count = count;
        this.duplicates = duplicates;
        this.unnecessary = unnecessary;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getDuplicates() {
        return duplicates;
    }

    public Integer getUnnecessary() {
        return unnecessary;
    }
}