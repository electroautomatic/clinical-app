package com.spartproject.clinicalpatients.clinicalapi.clinicalapi.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.dto.ClinicalDataRequest;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.models.ClinicalData;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.models.Patient;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.repos.ClinicalDataRepository;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.repos.PatientRepository;

@SpringBootTest
public class ClinicalDataControllerTest {

    @Mock
    private ClinicalDataRepository clinicalDataRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private ClinicalDataController clinicalDataController;

    @Test
    public void testGetAllClinicalData() {
        // Arrange
        List<ClinicalData> clinicalDataList = new ArrayList<>();
        clinicalDataList.add(new ClinicalData());
        clinicalDataList.add(new ClinicalData());

        when(clinicalDataRepository.findAll()).thenReturn(clinicalDataList);

        // Act
        List<ClinicalData> result = clinicalDataController.getAllClinicalData();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetClinicalDataById() {
        // Arrange
        Long id = 1L;
        ClinicalData clinicalData = new ClinicalData();
        when(clinicalDataRepository.findById(id)).thenReturn(Optional.of(clinicalData));

        // Act
        ClinicalData result = clinicalDataController.getClinicalDataById(id);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testUpdateClinicalData() {
        // Arrange
        Long id = 1L;
        ClinicalData clinicalData = new ClinicalData();
        ClinicalData updatedClinicalData = new ClinicalData();
        when(clinicalDataRepository.findById(id)).thenReturn(Optional.of(clinicalData));
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(updatedClinicalData);

        // Act
        ClinicalData result = clinicalDataController.updateClinicalData(id, new ClinicalData());

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testDeleteClinicalData() {
        // Arrange
        Long id = 1L;
        when(clinicalDataRepository.findById(id)).thenReturn(Optional.of(new ClinicalData()));

        // Act
        ResponseEntity<?> response = clinicalDataController.deleteClinicalData(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateClinicalData() {
        // Arrange
        ClinicalDataRequest request = new ClinicalDataRequest();
        request.setPatientId(1L);
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(new Patient()));
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(new ClinicalData());

        // Act
        ClinicalData result = clinicalDataController.createClinicalData(request);

        // Assert
        assertNotNull(result);
    }
}

