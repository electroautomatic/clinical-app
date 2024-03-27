package com.spartproject.clinicalpatients.clinicalapi.clinicalapi.controllers;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.exeption.ResourceNotFoundException;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.models.Patient;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.repos.PatientRepository;

import io.micrometer.common.lang.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/patients")
@CrossOrigin(origins = "*")
public class PatientController {
    private final Logger logger = LoggerFactory.getLogger(PatientController.class);
    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        try {
            Patient savedPatient = patientRepository.save(patient);
            logger.info("Received request to create new patient");
            return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = "application/json")
    public List<Patient> getAllPatients() {
        logger.info("Getting all patients");
        return patientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        try {
            Patient patient = patientRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
            logger.info("Getting patient with id {}", id);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Patient not found with id " + id);
                });

        patient.setName(patientDetails.getName());
        patient.setLastName(patientDetails.getLastName());
        patient.setAge(patientDetails.getAge());

        ResponseEntity<Patient> responseEntity = new ResponseEntity<>(patientRepository.save(patient), HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable Long id) {
        try {
            if (!patientRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            patientRepository.deleteById(id);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}






