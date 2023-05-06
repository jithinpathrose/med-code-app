package com.code.app.service;

import com.code.app.dto.MedCodeDetails;

import java.util.List;

/**
 * service interface
 */
public interface MedCodeService {

    public Boolean saveAll(List<MedCodeDetails> medCodeDetails);

    public List<MedCodeDetails> fetchAll();

    public MedCodeDetails fetchById(String code);

    public Boolean deleteAll();
}
