package com.dn.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class Rendering {

    @Id
    private final String documentId;
    private final String page;
    private final String UID;
    private final List<String> starts;
    private final List<String> getRenderings;
}