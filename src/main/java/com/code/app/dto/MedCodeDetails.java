package com.code.app.dto;


import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MedCodeDetails {


    public MedCodeDetails() {
        // No-argument constructor
    }



    private String source;

    private String codeListCode;

    @Id
    private String code;

    private String displayValue;

    private String longDescription;

    private Date fromDate;

    private Date toDate;

    private Integer sortingPriority;
}
