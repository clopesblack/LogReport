package com.dn.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class Rendering {

    @Id
    private final Long documentId;
    private final Integer page;
    private final String UID;
    private final List<LocalDateTime> starts;
    private final List<LocalDateTime> getRenderings;

    //TODO mabe use the lombok builder?
    public Rendering(final Long documentId, final Integer page, final String uid,
                     final List<LocalDateTime> starts, final List<LocalDateTime> getRenderings) {
        this.documentId = documentId;
        this.page = page;
        this.UID = uid;
        this.starts = starts;
        this.getRenderings = getRenderings;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public Integer getPage() {
        return page;
    }

    public String getUID() {
        return UID;
    }

    public List<LocalDateTime> getStarts() {
        return starts;
    }

    public List<LocalDateTime> getGetRenderings() {
        return getRenderings;
    }
}