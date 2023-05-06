package com.code.app.service;

import com.code.app.dto.MedCodeDetails;
import com.code.app.repo.MedCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MedCodeServiceImpl implements MedCodeService {

    @Autowired
    private MedCodeRepository medCodeRepository;

    @Override
    public Boolean saveAll(List<MedCodeDetails> medCodeDetails) {
        List<MedCodeDetails> result = medCodeDetails != null ? medCodeRepository.saveAll(medCodeDetails) : null;
        if (null != result) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<MedCodeDetails> fetchAll() {
        return this.medCodeRepository.findAll();
    }

    @Override
    public MedCodeDetails fetchById(String code) {
        return this.medCodeRepository.findById(code).get();
    }

    @Override
    public Boolean deleteAll() {
        this.medCodeRepository.deleteAll();
        return true;
    }
}
