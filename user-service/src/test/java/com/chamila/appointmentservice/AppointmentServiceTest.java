package com.chamila.appointmentservice;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Test
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private App appointmentService;

    public AppointmentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAppointment() {
        Appointment appointment = new Appointment(1L, 1L, 2L, LocalDateTime.now(), "Scheduled", "Consultation");
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        Appointment created = appointmentService.createAppointment(appointment);
        assertNotNull(created);
        assertEquals(appointment.getId(), created.getId());
    }
}

