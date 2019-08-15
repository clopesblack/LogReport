package com.dn.model;

import lombok.Builder;
import lombok.Getter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Builder
@Getter
@XmlRootElement
public class Report {

    private final List<Rendering> rendering;
    private final Summary summary;
}