package com.dn.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Report {

    private final List<Rendering> rendering;
    private final Summary summary;

    public Report(final List<Rendering> rendering, final Summary summary) {
        this.rendering = rendering;
        this.summary = summary;
    }
}