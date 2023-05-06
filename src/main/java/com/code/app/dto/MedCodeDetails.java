package com.code.app.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
public class MedCodeDetails {


    @Id
    private String code;

    private String source;

    private String codeListCode;

    private String displayValue;

    private String longDescription;

    private Date fromDate;

    private Date toDate;

    private int sortingPriority;
}
