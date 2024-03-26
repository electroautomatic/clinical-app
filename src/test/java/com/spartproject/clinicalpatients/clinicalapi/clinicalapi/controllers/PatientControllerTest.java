package com.spartproject.clinicalpatients.clinicalapi.clinicalapi.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.models.Patient;
import com.spartproject.clinicalpatients.clinicalapi.clinicalapi.repos.PatientRepository;



@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientController patientController;

    @Test
    public void testCreatePatient() {
        // given
        Patient inputPatient = new Patient("John", "Doe", 25);

        // when
        when(patientRepository.save(inputPatient)).thenReturn(inputPatient);
        ResponseEntity<Patient> result = patientController.createPatient(inputPatient);

        // then
        assertThat(result.getBody()).isEqualTo(inputPatient);
    }

    @Test
    public void testGetAllPatients() {
        // given
        List<Patient> mockPatients = new ArrayList<>();
        mockPatients.add(new Patient("Jane", "Doe", 30));
        mockPatients.add(new Patient("Jim", "Bean", 35));

        // when
        when(patientRepository.findAll()).thenReturn(mockPatients);
        List<Patient> result = patientController.getAllPatients();

        // then
        assertThat(result).isEqualTo(mockPatients);
    }

    @Test
    public void testGetPatientById() {
        // given
        Long id = 1L;
        Patient mockPatient = new Patient("John", "Doe", 25);

        // when
        when(patientRepository.findById(id)).thenReturn(Optional.of(mockPatient));
        ResponseEntity<Patient> result = patientController.getPatientById(id);

        // then
        assertThat(result.getBody()).isEqualTo(mockPatient);
    }

    @Test
    public void testDeletePatient() {
        // given
        Long id = 1L;
        Patient patient = new Patient("John", "Doe", 25);

        // when
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // when
        patientController.deletePatient(id);

        // then
        // test passes if no exception is thrown
    }

    @Test
    public void testUpdatePatient() {
        // Arrange
        Long id = 1L;
        Patient existingPatient = new Patient("John", "Doe", 30);
        Patient updatedPatientDetails = new Patient("Updated", "Details", 40);

        // Stubbing repository method findById
        when(patientRepository.findById(id)).thenReturn(Optional.of(existingPatient));
        // Stubbing repository method save
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatientDetails);

        // Act
        ResponseEntity<Patient> updatedPatientResponse = patientController.updatePatient(id, updatedPatientDetails);

        // Get the Patient object from the ResponseEntity
        Patient updatedPatient = updatedPatientResponse.getBody();

        // Assert
        assertNotNull(updatedPatient);
        assertEquals(updatedPatientDetails.getName(), updatedPatient.getName());
        assertEquals(updatedPatientDetails.getLastName(), updatedPatient.getLastName());
        assertEquals(updatedPatientDetails.getAge(), updatedPatient.getAge());
        verify(patientRepository, times(1)).findById(id);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void testDeletePatientNotFound() {
        Long id = 1L;

        // Stubbing repository method findById
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientController.deletePatient(id);
        });

        assertEquals("Patient not found with id " + id, exception.getMessage());
        verify(patientRepository, times(1)).findById(id);
        verify(patientRepository, never()).deleteById(anyLong());
    }
}






