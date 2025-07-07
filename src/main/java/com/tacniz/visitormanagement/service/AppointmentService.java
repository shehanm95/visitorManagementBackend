package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.model.Appointment;
import com.tacniz.visitormanagement.repo.AppointmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public List<Appointment> getAllFutureAppointments() {
        return appointmentRepository.findFutureAppointments(LocalDateTime.now());
    }

    public List<Appointment> getFutureAppointmentsFrom(LocalDateTime fromDateTime) {
        return appointmentRepository.findByDateAfter(fromDateTime);
    }

    public List<Appointment> getFutureAppointmentsFromDate(LocalDateTime fromDate) {
        if (fromDate == null) {
            throw new IllegalArgumentException("Date parameter cannot be null");
        }
        return appointmentRepository.findByDateAfter(fromDate);
    }

    @Transactional
    public List<Appointment> saveAllAppointments(List<Appointment> appointments) {
        // Add any validation/business logic here
        return appointmentRepository.saveAll(appointments);
    }
}