package com.spartproject.clinicalpatients.clinicalapi.clinicalapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.models.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}

