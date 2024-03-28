package com.spartproject.clinicalpatients.clinicalapi.clinicalapi.controllers;

import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.exeption.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.dto.ClinicalDataRequest;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.models.ClinicalData;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.models.Patient;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.repos.ClinicalDataRepository;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.repos.PatientRepository;

import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/clinicaldata")
@CrossOrigin(origins = "*")
public class ClinicalDataController {

    @Autowired
    private ClinicalDataRepository clinicalDataRepository;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    public ClinicalData createClinicalData(@RequestBody ClinicalData clinicalData) {
        return clinicalDataRepository.save(clinicalData);
    }

    @GetMapping
    public List<ClinicalData> getAllClinicalData() {
        return clinicalDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ClinicalData>> getClinicalDataByPatientId(@PathVariable Long id) {
        List<ClinicalData> clinicalDataList = clinicalDataRepository.findByPatientId(id);
        return new ResponseEntity<>(clinicalDataList, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ClinicalData updateClinicalData(@PathVariable Long id, @RequestBody ClinicalData clinicalDataDetails) {
        ClinicalData clinicalData = clinicalDataRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Patient not found with id " + id);
                });

        clinicalData.setComponentName(clinicalDataDetails.getComponentName());
        clinicalData.setComponentValue(clinicalDataDetails.getComponentValue());
        clinicalData.setMeasuredDateTime(clinicalDataDetails.getMeasuredDateTime());

        return clinicalDataRepository.save(clinicalData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClinicalData(@PathVariable Long id) {
        clinicalDataRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Patient not found with id " + id);
                });

        clinicalDataRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/clinicals")
    public ClinicalData createClinicalData(@RequestBody ClinicalDataRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId()).orElse(null);

        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }

        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setPatient(patient);
        clinicalData.setComponentName(request.getComponentName());
        clinicalData.setComponentValue(request.getComponentValue());
        return clinicalDataRepository.save(clinicalData);
    }

}

