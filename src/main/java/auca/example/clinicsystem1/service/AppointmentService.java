package auca.example.clinicsystem1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.example.clinicsystem1.model.domain.Appointment;
import auca.example.clinicsystem1.model.domain.AppointmentStatus;
import auca.example.clinicsystem1.model.domain.Doctor;
import auca.example.clinicsystem1.model.domain.Patient;
import auca.example.clinicsystem1.repository.AppointmentRepository;
import auca.example.clinicsystem1.repository.DoctorRepository;
import auca.example.clinicsystem1.repository.PatientRepository;
import auca.example.clinicsystem1.repository.PrescriptionRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PrescriptionRepository prescriptionRepo;


    public String saveAppointment(Appointment appointment) {
        if (appointment.getAppointmentDate() != null && appointment.getAppointmentDate().isBefore(LocalDate.now())) {
            return "Appointment date cannot be in the past";
        }
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        if (appointment.getPatient() != null && appointment.getPatient().getId() != null) {
            Patient patient = patientRepo.findById(appointment.getPatient().getId()).orElse(null);
            if (patient == null) {
                return "Patient not found";
            }
            appointment.setPatient(patient);
        } else {
            return "Patient is required";
        }

        if (appointment.getDoctor() != null && appointment.getDoctor().getId() != null) {
            Doctor doctor = doctorRepo.findById(appointment.getDoctor().getId()).orElse(null);
            if (doctor == null) {
                return "Doctor not found";
            }
            appointment.setDoctor(doctor);
        } else {
            return "Doctor is required";
        }

        appointmentRepo.save(appointment);
        return "Appointment saved successfully";
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public Appointment getAppointmentById(UUID id) {
        return appointmentRepo.findById(id).orElse(null);
    }

    public String updateAppointment(UUID id, Appointment newData) {
        Appointment appointment = appointmentRepo.findById(id).orElse(null);
        if (appointment == null) {
            return "Appointment not found";
        }

        appointment.setAppointmentDate(newData.getAppointmentDate());
        appointment.setReason(newData.getReason());
        // Status is not changed here per B2 rule

        if (newData.getPatient() != null && newData.getPatient().getId() != null) {
            Patient patient = patientRepo.findById(newData.getPatient().getId()).orElse(null);
            if (patient == null) {
                return "Patient not found";
            }
            appointment.setPatient(patient);
        }

        if (newData.getDoctor() != null && newData.getDoctor().getId() != null) {
            Doctor doctor = doctorRepo.findById(newData.getDoctor().getId()).orElse(null);
            if (doctor == null) {
                return "Doctor not found";
            }
            appointment.setDoctor(doctor);
        }

        appointmentRepo.save(appointment);
        return "Appointment updated successfully";
    }

    public String deleteAppointment(UUID id) {
        Appointment appointment = appointmentRepo.findById(id).orElse(null);
        if (appointment == null) {
            return "Appointment not found";
        }
        if (prescriptionRepo.existsByAppointment(appointment)) {
            return "That appointment has prescriptions, delete them first";
        }
        appointmentRepo.deleteById(id);
        return "Appointment deleted successfully";
    }


    public String updateStatus(UUID id, AppointmentStatus status) {
        Appointment appointment = appointmentRepo.findById(id).orElse(null);
        if (appointment == null) {
            return "The appointment with that id does not exist";
        }
        AppointmentStatus current = appointment.getStatus();
        boolean allowed = false;
        if (current == AppointmentStatus.SCHEDULED) {
            if (status == AppointmentStatus.CONFIRMED || status == AppointmentStatus.CANCELLED) {
                allowed = true;
            }
        } else if (current == AppointmentStatus.CONFIRMED) {
            if (status == AppointmentStatus.COMPLETED || status == AppointmentStatus.CANCELLED) {
                allowed = true;
            }
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + status;
        }
        appointment.setStatus(status);
        appointmentRepo.save(appointment);
        return "Appointment status updated successfully";
    }

    public String rescheduleAppointment(UUID id, LocalDate newDate) {
        Appointment appointment = appointmentRepo.findById(id).orElse(null);
        if (appointment == null) {
            return "The appointment with that id does not exist";
        }
        if (appointment.getStatus() == AppointmentStatus.COMPLETED || appointment.getStatus() == AppointmentStatus.CANCELLED) {
            return "A " + appointment.getStatus() + " appointment cannot be rescheduled";
        }
        if (newDate.isBefore(LocalDate.now())) {
            return "Appointment date cannot be in the past";
        }
        if (appointmentRepo.existsByDoctorAndAppointmentDateAndStatusNot(appointment.getDoctor(), newDate, AppointmentStatus.CANCELLED)) {
            return "Doctor is already booked on that date";
        }
        appointment.setAppointmentDate(newDate);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointmentRepo.save(appointment);
        return "Appointment rescheduled successfully";
    }
}

