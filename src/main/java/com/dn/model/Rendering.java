package com.dn.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class Rendering {

    @Id
    private final Integer documentId;
    private final Integer page;
    private final String UID;
    private final List<LocalDateTime> starts;
    private final List<LocalDateTime> getRenderings;
}