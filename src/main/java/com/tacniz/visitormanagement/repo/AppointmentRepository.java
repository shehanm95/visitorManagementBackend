package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.date > :currentDate ORDER BY a.date ASC")
    List<Appointment> findFutureAppointments(@Param("currentDate") LocalDateTime currentDate);

    List<Appointment> findByDateAfter(LocalDateTime currentDateTime);

}