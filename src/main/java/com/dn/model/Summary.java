package com.dn.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Summary {

    private final Integer count;
    private final Integer duplicates;
    private final Integer unnecessary;
}