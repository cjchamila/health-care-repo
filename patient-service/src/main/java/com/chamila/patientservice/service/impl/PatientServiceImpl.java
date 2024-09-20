package com.chamila.patientservice.service.impl;

import com.chamila.patientservice.exception.ResourceNotFoundException;
import com.chamila.patientservice.model.Patient;
import com.chamila.patientservice.repository.PatientRepository;
import com.chamila.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }

    @Override
    public List<Patient> getAllPatients() {
        //TODO : Add PAGINATION
        return patientRepository.findAll();
    }

    @Override
    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient existingPatient = getPatientById(id);
        existingPatient.setFirstName(patientDetails.getFirstName());
        existingPatient.setLastName(patientDetails.getLastName());
        existingPatient.setDateOfBirth(patientDetails.getDateOfBirth());
        existingPatient.setEmail(patientDetails.getEmail());
        existingPatient.setPhoneNumber(patientDetails.getPhoneNumber());
        existingPatient.setAddress(patientDetails.getAddress());

        return patientRepository.save(existingPatient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
    }
}
