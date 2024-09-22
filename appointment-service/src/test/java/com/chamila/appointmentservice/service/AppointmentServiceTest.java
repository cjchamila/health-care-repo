package com.chamila.appointmentservice.service;

import com.chamila.appointmentservice.exception.ResourceNotFoundException;
import com.chamila.appointmentservice.model.Appointment;
import com.chamila.appointmentservice.repository.AppointmentRepository;
import com.chamila.appointmentservice.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService=new AppointmentServiceImpl();

    private Appointment appointment;

    public AppointmentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appointment = new Appointment(1L, 1L, 2L, LocalDateTime.now(), "Scheduled", "Consultation");
    }

    @Test
    void testCreateAppointment() {
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment created = appointmentService.createAppointment(appointment);
        assertNotNull(created);
        assertEquals(appointment.getId(), created.getId());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testGetAppointmentById() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        Appointment found = appointmentService.getAppointmentById(1L);
        assertNotNull(found);
        assertEquals(appointment.getId(), found.getId());
    }

    @Test
    void testGetAppointmentByIdNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.getAppointmentById(1L);
        });

        assertEquals("Appointment not found with id 1", exception.getMessage());
    }

    @Test
    void testGetAppointmentsByPatientId() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        when(appointmentRepository.findByPatientId(1L)).thenReturn(appointments);

        List<Appointment> foundAppointments = appointmentService.getAppointmentsByPatientId(1L);
        assertNotNull(foundAppointments);
        assertEquals(1, foundAppointments.size());
        assertEquals(appointment.getId(), foundAppointments.get(0).getId());
    }

    @Test
    void testGetAppointmentsByDoctorId() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        when(appointmentRepository.findByDoctorId(2L)).thenReturn(appointments);

        List<Appointment> foundAppointments = appointmentService.getAppointmentsByDoctorId(2L);
        assertNotNull(foundAppointments);
        assertEquals(1, foundAppointments.size());
        assertEquals(appointment.getId(), foundAppointments.get(0).getId());
    }

    @Test
    void testUpdateAppointment() {
        Appointment updatedAppointment = new Appointment(1L, 1L, 2L, LocalDateTime.now().plusDays(1), "Rescheduled", "Follow-up");
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(updatedAppointment);

        Appointment result = appointmentService.updateAppointment(1L, updatedAppointment);
        assertNotNull(result);
        assertEquals(updatedAppointment.getAppointmentDateTime(), result.getAppointmentDateTime());
        assertEquals("Rescheduled", result.getStatus());
    }

    @Test
    void testUpdateAppointmentNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.updateAppointment(1L, appointment);
        });

        assertEquals("Appointment not found with id 1", exception.getMessage());
    }

    @Test
    void testDeleteAppointment() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        assertDoesNotThrow(() -> appointmentService.deleteAppointment(1L));
        verify(appointmentRepository, times(1)).delete(appointment);
    }

    @Test
    void testDeleteAppointmentNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.deleteAppointment(1L);
        });

        assertEquals("Appointment not found with id 1", exception.getMessage());
    }
}
