package com.dn.model;

import lombok.Builder;
import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Builder
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class Summary {

    private final Integer count;
    private final Integer duplicates;
    private final Integer unnecessary;
}