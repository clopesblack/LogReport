package com.dn.model;

import java.util.List;

public class Report {

    private final List<Rendering> rendering;
    private final Summary summary;

    public Report(final List<Rendering> rendering, final Summary summary) {
        this.rendering = rendering;
        this.summary = summary;
    }

    public List<Rendering> getRendering() {
        return rendering;
    }

    public Summary getSummary() {
        return summary;
    }
}