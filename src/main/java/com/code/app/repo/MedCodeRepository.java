package com.code.app.repo;

import com.code.app.dto.MedCodeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedCodeRepository extends JpaRepository<MedCodeDetails, String> {
}
