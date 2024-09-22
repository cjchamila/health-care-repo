package com.chamila.appointmentservice.service;

import com.chamila.appointmentservice.model.Appointment;

import java.util.List;

public interface AppointmentService {

    public Appointment createAppointment(Appointment appointment) ;

    public Appointment getAppointmentById(Long id) ;

    public List<Appointment> getAppointmentsByPatientId(Long patientId) ;

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) ;

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) ;

    public void deleteAppointment(Long id) ;

}
