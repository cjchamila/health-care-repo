package com.chamila.patientservice.service;

import com.chamila.patientservice.exception.ResourceNotFoundException;
import com.chamila.patientservice.model.Patient;
import com.chamila.patientservice.repository.PatientRepository;
import com.chamila.patientservice.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @InjectMocks
    private PatientService patientService=new PatientServiceImpl();

    @Mock
    private PatientRepository patientRepository;

    private Patient patient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample Patient object to be used in tests
        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setEmail("john.doe@example.com");
        patient.setPhoneNumber("1234567890");
    }

    // Test case for creating a new patient
    @Test
    void testCreatePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient createdPatient = patientService.createPatient(patient);

        assertNotNull(createdPatient);
        assertEquals(patient.getFirstName(), createdPatient.getFirstName());
        assertEquals(patient.getEmail(), createdPatient.getEmail());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    // Test case for getting a patient by ID
    @Test
    void testGetPatientById_Success() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        Patient foundPatient = patientService.getPatientById(1L);

        assertNotNull(foundPatient);
        assertEquals(patient.getId(), foundPatient.getId());
        assertEquals(patient.getFirstName(), foundPatient.getFirstName());
        verify(patientRepository, times(1)).findById(anyLong());
    }

    // Test case for getting a patient by ID when the patient is not found
    @Test
    void testGetPatientById_NotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            patientService.getPatientById(1L);
        });

        verify(patientRepository, times(1)).findById(anyLong());
    }

    // Test case for getting all patients
    @Test
    void testGetAllPatients() {
        List<Patient> patients = Arrays.asList(patient, new Patient());
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> allPatients = patientService.getAllPatients();

        assertEquals(2, allPatients.size());
        verify(patientRepository, times(1)).findAll();
    }

    // Test case for updating a patient
    @Test
    void testUpdatePatient() {
        Patient updatedPatientDetails = new Patient();
        updatedPatientDetails.setFirstName("Jane");
        updatedPatientDetails.setLastName("Smith");
        updatedPatientDetails.setEmail("jane.smith@example.com");
        updatedPatientDetails.setPhoneNumber("0987654321");

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatientDetails);

        Patient updatedPatient = patientService.updatePatient(1L, updatedPatientDetails);

        assertEquals(updatedPatientDetails.getFirstName(), updatedPatient.getFirstName());
        assertEquals(updatedPatientDetails.getEmail(), updatedPatient.getEmail());
        verify(patientRepository, times(1)).findById(anyLong());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    // Test case for deleting a patient
    @Test
    void testDeletePatient() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).delete(any(Patient.class));
    }

    // Test case for deleting a patient that does not exist
    @Test
    void testDeletePatient_NotFound() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            patientService.deletePatient(1L);
        });

        verify(patientRepository, times(1)).findById(anyLong());
    }
}
