package auca.example.clinicsystem1.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.example.clinicsystem1.model.domain.Appointment;
import auca.example.clinicsystem1.model.domain.AppointmentStatus;
import auca.example.clinicsystem1.model.domain.Doctor;
import auca.example.clinicsystem1.model.domain.Patient;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    // B3
    boolean existsByDoctorAndAppointmentDateAndStatusNot(Doctor doctor, LocalDate appointmentDate, AppointmentStatus status);

    // D1
    List<Appointment> findByDoctorAndAppointmentDateAndStatusNot(Doctor doctor, LocalDate appointmentDate, AppointmentStatus status);

    // D2
    List<Appointment> findByPatientAndAppointmentDateGreaterThanEqualAndStatusInOrderByAppointmentDateAsc(
            Patient patient,
            LocalDate appointmentDate,
            List<AppointmentStatus> statuses
    );

    // D4
    long countByAppointmentDate(LocalDate appointmentDate);
    long countByStatus(AppointmentStatus status);
}

