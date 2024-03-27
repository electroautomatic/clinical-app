package com.spartproject.clinicalpatients.clinicalapi.clinicalapi.repos;

import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.models.ClinicalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Long> {
    List<ClinicalData> findByPatientId(Long patientId);
}