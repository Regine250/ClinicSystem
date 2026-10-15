package auca.example.clinicsystem1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.example.clinicsystem1.model.domain.Appointment;
import auca.example.clinicsystem1.model.domain.AppointmentStatus;
import auca.example.clinicsystem1.model.domain.Patient;
import auca.example.clinicsystem1.repository.AppointmentRepository;
import auca.example.clinicsystem1.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    public String savePatient(Patient patient) {
        patientRepo.save(patient);
        return "Patient saved successfully";
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public Patient getPatientById(UUID id) {
        return patientRepo.findById(id).orElse(null);
    }

    public String updatePatient(UUID id, Patient newData) {
        Patient patient = patientRepo.findById(id).orElse(null);
        if (patient == null) {
            return "Patient not found";
        }
        patient.setFirstName(newData.getFirstName());
        patient.setLastName(newData.getLastName());
        patient.setDateOfBirth(newData.getDateOfBirth());
        patient.setSsNumber(newData.getSsNumber());
        patientRepo.save(patient);
        return "Patient updated successfully";
    }

    public String deletePatient(UUID id) {
        if (!patientRepo.existsById(id)) {
            return "Patient not found";
        }
        patientRepo.deleteById(id);
        return "Patient deleted successfully";
    }

    public List<Appointment> getUpcomingAppointments(String ssNumber) {
        Optional<Patient> patientOpt = patientRepo.findBySsNumber(ssNumber);
        if (patientOpt.isEmpty()) {
            return null;
        }
        return appointmentRepo.findByPatientAndAppointmentDateGreaterThanEqualAndStatusInOrderByAppointmentDateAsc(
                patientOpt.get(),
                LocalDate.now(),
                List.of(AppointmentStatus.SCHEDULED, AppointmentStatus.CONFIRMED)
        );
    }
}

